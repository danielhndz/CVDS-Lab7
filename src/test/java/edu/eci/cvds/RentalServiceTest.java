package edu.eci.cvds;

import com.google.inject.Inject;
import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.samples.entities.ItemRentado;
import edu.eci.cvds.samples.entities.TipoItem;
import edu.eci.cvds.samples.services.RentalServicesException;
import edu.eci.cvds.samples.services.ServiciosAlquiler;
import edu.eci.cvds.samples.services.ServiciosAlquilerFactory;
import edu.eci.cvds.samples.services.impl.ServiciosAlquilerItemsStub;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class RentalServiceTest {

    @SuppressWarnings("unused")
    @Inject
    private SqlSession sqlSession;

    private final SqlSessionFactory sqlSessionFactory;
    private SqlSession sqlSs;

    private final ServiciosAlquiler serviciosAlquiler;
    private final ServiciosAlquiler serviciosAlquilerItemStub;

    public RentalServiceTest() {
        serviciosAlquiler = ServiciosAlquilerFactory.getInstance().getServiciosAlquilerTesting();
        serviciosAlquilerItemStub = new ServiciosAlquilerItemsStub();
        sqlSessionFactory = getSqlSessionFactory();
    }

    @Before
    public void setUp() {
        sqlSs = sqlSessionFactory.openSession();
    }

    @After
    public void clearDB() {
        try {
            serviciosAlquiler.limpiarClientes();
            serviciosAlquiler.limpiarItems();
            serviciosAlquiler.limpiarItemsRentados();
            serviciosAlquiler.limpiarTiposItem();
        } catch (RentalServicesException e) {
            e.printStackTrace();
        }
        sqlSs.commit();
        sqlSs.close();
    }

    @Test
    public void valorMultaRetrasoPorDia() {
        try {
            int itemId = 1;

            TipoItem tipoItem = serviciosAlquilerItemStub.consultarTipoItem(itemId);
            Item item = serviciosAlquilerItemStub.consultarItem(itemId);

            serviciosAlquiler.registrarTipoItem(tipoItem);
            serviciosAlquiler.registrarItem(item);

            long expected = item.getTarifaxDia();
            long actual = serviciosAlquiler.valorMultaRetrasoPorDia(itemId);

            Assert.assertEquals(expected, actual);
        } catch (RentalServicesException e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Esta es la prueba principal, se evalúan los siguientes métodos de ServiciosAlquilerImpl:
     *
     * registrarCliente(Cliente cliente)
     *
     * consultarCliente(long documento)
     *
     * registrarAlquilerClienteConId(
     *      int idItemRentado,
     *      Date fechaInicioRenta,
     *      long documento,
     *      Item item,
     *      int numDays)
     *
     * registrarItem(ItemRentado itemRentado)
     *
     * registrarTipoItem(TipoItem tipoItem)
     *
     * consultarItemsCliente(long documento)
     */
    @Test
    public void MainTest_consultarCliente() {
        try {
            long documento = 1026585664L;

            Cliente expected = serviciosAlquilerItemStub.consultarCliente(documento);
            serviciosAlquiler.registrarCliente(expected);
            Cliente actual = serviciosAlquiler.consultarCliente(documento);

            ArrayList<ItemRentado> rentadosExpected = expected.getRentados();
            if (actual.getRentados().size() == 0 && rentadosExpected.size() != 0) {

                for (ItemRentado itemRentado : rentadosExpected) {

                    LocalDate localDate1 = itemRentado.getFechaInicioRenta().toLocalDate();
                    LocalDate localDate2 = itemRentado.getFechaFinRenta().toLocalDate();
                    long daysBetween = ChronoUnit.DAYS.between(localDate1, localDate2);

                    serviciosAlquiler.registrarAlquilerClienteConId(
                            itemRentado.getId(),
                            itemRentado.getFechaInicioRenta(),
                            documento,
                            itemRentado.getItem(),
                            (int) daysBetween
                    );

                    actual = serviciosAlquiler.consultarCliente(documento);

                    int currentIndex = rentadosExpected.indexOf(itemRentado);
                    Item currentItem = actual.getRentados().get(currentIndex).getItem();

                    if (currentItem == null && itemRentado.getItem() != null) {
                        serviciosAlquiler.registrarItem(itemRentado.getItem());
                        actual = serviciosAlquiler.consultarCliente(documento);
                        currentItem = actual.getRentados().get(currentIndex).getItem();
                    }

                    assert currentItem != null;
                    if (currentItem.getTipo() == null && itemRentado.getItem().getTipo() != null) {
                        serviciosAlquiler.registrarTipoItem(itemRentado.getItem().getTipo());
                        actual = serviciosAlquiler.consultarCliente(documento);
                    }
                }
            }

            Assert.assertEquals(expected.toString(), actual.toString());

            List<ItemRentado> itemsClienteExpected = serviciosAlquilerItemStub.consultarItemsCliente(documento);
            List<ItemRentado> itemsClienteActual = serviciosAlquiler.consultarItemsCliente(documento);

            Assert.assertEquals(itemsClienteExpected.toString(), itemsClienteActual.toString());
        } catch (RentalServicesException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void consultarCliente() {
        try {
            poblarItemsStub();

            long documento = 1026585664L;
            Cliente expected = serviciosAlquilerItemStub.consultarCliente(documento);
            Cliente actual = serviciosAlquiler.consultarCliente(documento);

            Assert.assertEquals(expected.toString(), actual.toString());
        } catch (RentalServicesException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void consultarClientes() {
        try {
            List<Cliente> expected = serviciosAlquilerItemStub.consultarClientes();
            for (Cliente cliente : expected) {
                cliente.setRentados(new ArrayList<>());
                serviciosAlquiler.registrarCliente(cliente);
            }
            List<Cliente> actual = serviciosAlquiler.consultarClientes();

            Assert.assertEquals(expected.toString(), actual.toString());
        } catch (RentalServicesException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void consultarItem() {
        try {
            int itemId = 1;

            Item expected = serviciosAlquilerItemStub.consultarItem(itemId);
            TipoItem tipoItem = expected.getTipo();
            serviciosAlquiler.registrarTipoItem(tipoItem);
            serviciosAlquiler.registrarItem(expected);
            Item actual = serviciosAlquiler.consultarItem(itemId);

            Assert.assertEquals(expected.toString(), actual.toString());
        } catch (RentalServicesException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void consultarItemsDisponibles() {
        try {
            poblarItemsStub();

            List<Item> expected = serviciosAlquilerItemStub.consultarItemsDisponibles();
            List<Item> actual = serviciosAlquiler.consultarItemsDisponibles();

            Assert.assertEquals(expected.toString(), actual.toString());
        } catch (RentalServicesException e) {
            Assert.fail(e.getMessage());
        }
    }
    
    @Test
    public void consultarMultaAlquiler() {
        try {
            int idItemRentado = 1;
            
            poblarItemsStub();
            
            Date returnDate = new Date(System.currentTimeMillis());
            long expected = serviciosAlquilerItemStub.consultarMultaAlquiler(idItemRentado, returnDate);
            long actual = serviciosAlquiler.consultarMultaAlquiler(idItemRentado, returnDate);

            Assert.assertEquals(expected, actual);
        } catch (RentalServicesException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void consultarTipoItem() {
        try {
            int tipoItemId = 1;

            TipoItem expected = serviciosAlquilerItemStub.consultarTipoItem(1);
            serviciosAlquiler.registrarTipoItem(expected);
            TipoItem actual = serviciosAlquiler.consultarTipoItem(tipoItemId);

            Assert.assertEquals(expected.toString(), actual.toString());
        } catch (RentalServicesException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void consultarTiposItem() {
        try {
            poblarItemsStub();

            List<TipoItem> expected = serviciosAlquilerItemStub.consultarTiposItem();
            List<TipoItem> actual = serviciosAlquiler.consultarTiposItem();

            Assert.assertEquals(expected.toString(), actual.toString());
        } catch (RentalServicesException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void consultarCostoAlquiler() {
        try {
            int idItem = 1;
            int numDays = 20;

            poblarItemsStub();

            long expected = serviciosAlquilerItemStub.consultarCostoAlquiler(idItem, numDays);
            long actual = serviciosAlquilerItemStub.consultarCostoAlquiler(idItem, numDays);

            Assert.assertEquals(expected, actual);
        } catch (RentalServicesException e) {
            Assert.fail(e.getMessage());
        }
    }

    private static SqlSessionFactory getSqlSessionFactory() {
        SqlSessionFactory sqlSessionFactory;
        InputStream inputStream;
        try {
            inputStream = Resources.getResourceAsStream("mybatis-config-h2.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e.getCause());
        }
        return sqlSessionFactory;
    }

    private void poblarItemsStub() throws RentalServicesException {
        TipoItem tipoItem1 = new TipoItem(1,"Vídeo");
        TipoItem tipoItem2 = new TipoItem(2,"Juego");
        TipoItem tipoItem3 = new TipoItem(3,"Música");

        serviciosAlquiler.registrarTipoItem(tipoItem1);
        serviciosAlquiler.registrarTipoItem(tipoItem2);
        serviciosAlquiler.registrarTipoItem(tipoItem3);

        Item item1 = new Item(tipoItem1, 1,
                "Los 4 Fantásticos",
                "Los 4 Fantásticos  es una película de superhéroes  basada en la serie de cómic homónima de Marvel.",
                java.sql.Date.valueOf("2005-06-08"), 2000, "DVD", "Ciencia Fiction");
        Item item2 = new Item(tipoItem2, 2,
                "Halo 3",
                "Halo 3 es un videojuego de disparos en primera persona desarrollado por Bungee Studios.",
                java.sql.Date.valueOf("2007-09-08"), 3000, "DVD", "Shooter");
        Item item3 = new Item(tipoItem3, 3,
                "Thriller",
                "Thriller es una canción interpretada por el cantante estadounidense Michael Jackson, compuesta por Rod Templeton y producida por Quincy Jones.",
                java.sql.Date.valueOf("1984-01-11"), 2000, "DVD", "Pop");
        Item item4 = new Item(tipoItem1, 4,
                "Los 4 Fantásticos",
                "Los 4 Fantásticos  es una película de superhéroes  basada en la serie de cómic homónima de Marvel.",
                java.sql.Date.valueOf("2005-06-08"), 2000, "DVD", "Ciencia Fiction");
        Item item5 = new Item(tipoItem2, 5,
                "Halo 3",
                "Halo 3 es un videojuego de disparos en primera persona desarrollado por Bungee Studios.",
                java.sql.Date.valueOf("2007-09-08"), 3000, "DVD", "Shooter");
        Item item6 = new Item(tipoItem3, 6,
                "Thriller",
                "Thriller es una canción interpretada por el cantante estadounidense Michael Jackson, compuesta por Rod Templeton y producida por Quincy Jones.",
                java.sql.Date.valueOf("1984-01-11"), 2000, "DVD", "Pop");

        serviciosAlquiler.registrarItem(item1);
        serviciosAlquiler.registrarItem(item2);
        serviciosAlquiler.registrarItem(item3);
        serviciosAlquiler.registrarItem(item4);
        serviciosAlquiler.registrarItem(item5);
        serviciosAlquiler.registrarItem(item6);

        ItemRentado ir1 = new ItemRentado(1, item1, java.sql.Date.valueOf("2017-01-01"), java.sql.Date.valueOf("2017-03-12"));
        ItemRentado ir2 = new ItemRentado(2, item2, java.sql.Date.valueOf("2017-01-04"), java.sql.Date.valueOf("2017-04-7"));
        ItemRentado ir3 = new ItemRentado(3, item3, java.sql.Date.valueOf("2017-01-07"), java.sql.Date.valueOf("2017-07-12"));
        ArrayList<ItemRentado> list1 = new ArrayList<>();
        list1.add(ir1);
        ArrayList<ItemRentado> list2 = new ArrayList<>();
        list2.add(ir2);
        ArrayList<ItemRentado> list3 = new ArrayList<>();
        list3.add(ir3);

        Cliente c1 = new Cliente("Oscar Alba", 1026585664, "6788952", "KRA 109#34-C30", "oscar@hotmail.com", false,list1);
        Cliente c2 = new Cliente("Carlos Ramirez", 1026585663, "6584562", "KRA 59#27-a22", "carlos@hotmail.com", false,list2);
        Cliente c3 = new Cliente("Ricardo Pinto", 1026585669, "4457863", "KRA 103#94-a77", "ricardo@hotmail.com", false,list3);
        serviciosAlquiler.registrarCliente(c1);
        serviciosAlquiler.registrarCliente(c2);
        serviciosAlquiler.registrarCliente(c3);

        LocalDate initialDate1 = ir1.getFechaInicioRenta().toLocalDate();
        LocalDate finalDate1 = ir1.getFechaFinRenta().toLocalDate();
        long daysBetween1 = ChronoUnit.DAYS.between(initialDate1, finalDate1);
        serviciosAlquiler.registrarAlquilerClienteConId(
                ir1.getId(),
                Date.valueOf(initialDate1),
                c1.getDocumento(),
                item1,
                (int) daysBetween1
        );

        LocalDate initialDate2 = ir2.getFechaInicioRenta().toLocalDate();
        LocalDate finalDate2 = ir2.getFechaFinRenta().toLocalDate();
        long daysBetween2 = ChronoUnit.DAYS.between(initialDate2, finalDate2);
        serviciosAlquiler.registrarAlquilerClienteConId(
                ir2.getId(),
                Date.valueOf(initialDate1),
                c2.getDocumento(),
                item2,
                (int) daysBetween2
        );

        LocalDate initialDate3 = ir3.getFechaInicioRenta().toLocalDate();
        LocalDate finalDate3 = ir3.getFechaFinRenta().toLocalDate();
        long daysBetween3 = ChronoUnit.DAYS.between(initialDate3, finalDate3);
        serviciosAlquiler.registrarAlquilerClienteConId(
                ir3.getId(),
                Date.valueOf(initialDate1),
                c3.getDocumento(),
                item3,
                (int) daysBetween3
        );
    }
}
