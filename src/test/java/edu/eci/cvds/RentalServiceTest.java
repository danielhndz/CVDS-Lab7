package edu.eci.cvds;

import com.google.inject.Inject;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.ItemMapper;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.samples.entities.TipoItem;
import edu.eci.cvds.samples.services.RentalServicesException;
import edu.eci.cvds.samples.services.ServiciosAlquiler;
import edu.eci.cvds.samples.services.ServiciosAlquilerFactory;
import edu.eci.cvds.samples.services.impl.ServiciosAlquilerItemsStub;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RentalServiceTest {

    @Inject
    private SqlSession sqlSession;

    private ServiciosAlquiler serviciosAlquiler;
    private ServiciosAlquiler serviciosAlquilerItemStub;

    public RentalServiceTest() {
        serviciosAlquiler = ServiciosAlquilerFactory.getInstance().getServiciosAlquilerTesting();
        serviciosAlquilerItemStub = new ServiciosAlquilerItemsStub();
    }

    @Before
    public void setUp() {
        System.out.println("--- setUp ---");
        /*try {
            System.out.println("TiposItem\n" + serviciosAlquiler.consultarTiposItem());
            System.out.println("Items\n" + serviciosAlquiler.consultarItems());
            System.out.println("Clientes\n" + serviciosAlquiler.consultarClientes());
            System.out.println("ItemRentado\n" + serviciosAlquiler.consultarItemsRentados());
        } catch (RentalServicesException e) {
            e.printStackTrace();
        }*/
    }

    @After
    public void clearDB() {
        /*System.out.println("--- clearDB ---");
        try {
            System.out.println("TiposItem\n" + serviciosAlquiler.consultarTiposItem());
            System.out.println("Items\n" + serviciosAlquiler.consultarItems());
            System.out.println("Clientes\n" + serviciosAlquiler.consultarClientes());
            System.out.println("ItemRentado\n" + serviciosAlquiler.consultarItemsRentados());

            serviciosAlquiler.limpiarClientes();
            serviciosAlquiler.limpiarItems();
            serviciosAlquiler.limpiarItemsRentados();
            serviciosAlquiler.limpiarTiposItem();


            System.out.println("TiposItem\n" + serviciosAlquiler.consultarTiposItem());
            System.out.println("Items\n" + serviciosAlquiler.consultarItems());
            System.out.println("Clientes\n" + serviciosAlquiler.consultarClientes());
            System.out.println("ItemRentado\n" + serviciosAlquiler.consultarItemsRentados());
        } catch (RentalServicesException e) {
            e.printStackTrace();
        }*/
    }

    @Test
    public void valorMultaRetrasoPorDia() {
        System.out.println("---------- valorMultaRetrasoPorDia ----------");
        try {
            int itemId = 1;

            // TipoItem tipoItem = serviciosAlquilerItemStub.consultarTipoItem(itemId);
            TipoItem tipoItem = new TipoItem(1,"Vídeo");

            // Item item = serviciosAlquilerItemStub.consultarItem(itemId);
            Item item = new Item(tipoItem, 1, "Los 4 Fantásticos", "Los 4 Fantásticos  es una película de superhéroes  basada en la serie de cómic homónima de Marvel.", java.sql.Date.valueOf("2005-06-08"), 2000, "DVD", "Ciencia Ficcion");
            long expected = item.getTarifaxDia();

            // System.out.println("(t=0)   TiposItem\n" + serviciosAlquiler.consultarTiposItem());
            serviciosAlquiler.registrarTipoItem(tipoItem);
            // System.out.println("(t=1)   TiposItem\n" + serviciosAlquiler.consultarTiposItem());

            // System.out.println("(t=0)   Items\n" + serviciosAlquiler.consultarItems());
            serviciosAlquiler.registrarItem(item);
            // ItemMapper itemMapper = sqlSession.getMapper(ItemMapper.class);
            // itemMapper.insertarItem(item);
            // sqlSession.commit();
            // sqlSession.close();
            // System.out.println("(t=1)   Items\n" + serviciosAlquiler.consultarItems());

            long actual = serviciosAlquiler.valorMultaRetrasoPorDia(itemId);

            Assert.assertEquals(expected, actual);
        } catch (RentalServicesException e) {
            Assert.assertTrue(false);
        }
    }

/*    @Test
    public void consultarCliente() {
        try {
            *//**
            long docu = 1026585664L;

            TipoItem tipoItem1 = new TipoItem(1,"Vídeo");
            Item item1 = new Item(tipoItem1, 1, "Los 4 Fantásticos", "Los 4 Fantásticos  es una película de superhéroes  basada en la serie de cómic homónima de Marvel.", java.sql.Date.valueOf("2005-06-08"), 2000, "DVD", "Ciencia Ficcion");
            ItemRentado ir1 = new ItemRentado(
                    0,
                    new Item(
                            new TipoItem(
                                    1,
                                    "Vídeo"),
                            1,
                            "Los 4 Fantásticos",
                            "Los 4 Fantásticos  es una película de superhéroes  basada en la serie de cómic homónima de Marvel.",
                            java.sql.Date.valueOf("2005-06-08"),
                            2000,
                            "DVD",
                            "Ciencia Ficcion"),
                    java.sql.Date.valueOf("2017-01-01"),
                    java.sql.Date.valueOf("2017-03-12"));
            ArrayList<ItemRentado> list1 = new ArrayList<>();
            list1.add(ir1);
            Cliente expected = new Cliente(
                    "Oscar Alba",
                    docu,
                    "6788952",
                    "KRA 109#34-C30",
                    "oscar@hotmail.com",
                    false,
                    list1);
            serviciosAlquiler.registrarCliente(expected);

            Cliente actual = serviciosAlquiler.consultarCliente(docu);

            System.out.println("\nconsultarTiposItem = " + serviciosAlquiler.consultarTiposItem());
            System.out.println("\nconsultarItems 0 = " + serviciosAlquiler.consultarItems());
            System.out.println("\nconsultarClientes 0 = " + serviciosAlquiler.consultarClientes());

            /**
            Cliente expected = serviciosAlquilerItemStub.consultarCliente(docu);

            System.out.println("\nexpected = " + expected.toString());


            System.out.println("\nconsultarTiposItem = " + serviciosAlquiler.consultarTiposItem());
            System.out.println("\nconsultarItems 0 = " + serviciosAlquiler.consultarItems());
            serviciosAlquiler.registrarItem(item1);
            System.out.println("\nconsultarItems 1 = " + serviciosAlquiler.consultarItems());
            System.out.println("\nconsultarClientes 0 = " + serviciosAlquiler.consultarClientes());
            serviciosAlquiler.registrarCliente(expected);
            System.out.println("\nconsultarClientes 1 = " + serviciosAlquiler.consultarClientes());
            System.out.println("\nconsultarItemsCliente 0 = " + serviciosAlquiler.consultarItemsCliente(docu));
            serviciosAlquiler.registrarAlquilerClienteConId(0, java.sql.Date.valueOf("2017-01-01"), docu, item1, 70);
            System.out.println("\nconsultarItemsCliente 1 = " + serviciosAlquiler.consultarItemsCliente(docu));
            Cliente actual = serviciosAlquiler.consultarCliente(docu);

            System.out.println("\nactual = " + actual.toString());

            Assert.assertEquals(expected.toString(), actual.toString());
            *//*


        } catch (RentalServicesException e) {
            e.printStackTrace();
            Assert.assertTrue(false);
        }
    }*/

    @Test
    public void consultarTipoItem() {
        System.out.println("---------- consultarTipoItem ----------");
        try {
            int tipoItemId = 1;

            TipoItem expected = serviciosAlquilerItemStub.consultarTipoItem(1);

            // System.out.println("(t=0)   TiposItem\n" + serviciosAlquiler.consultarTiposItem());
            serviciosAlquiler.registrarTipoItem(expected);
            // System.out.println("(t=1)   TiposItem\n" + serviciosAlquiler.consultarTiposItem());
            TipoItem actual = serviciosAlquiler.consultarTipoItem(tipoItemId);

            Assert.assertEquals(expected.toString(), actual.toString());
        } catch (RentalServicesException e) {
            Assert.assertTrue(false);
        }
    }

    @Test
    public void consultarItem() {
        System.out.println("---------- consultarItem ----------");
    }
}
