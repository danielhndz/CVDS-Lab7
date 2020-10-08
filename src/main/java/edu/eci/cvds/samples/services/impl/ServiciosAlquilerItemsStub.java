package edu.eci.cvds.samples.services.impl;

import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.samples.entities.ItemRentado;
import edu.eci.cvds.samples.entities.TipoItem;
import edu.eci.cvds.samples.services.RentalServicesException;
import edu.eci.cvds.samples.services.ServiciosAlquiler;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class ServiciosAlquilerItemsStub implements ServiciosAlquiler {

    private static final int MULTA_DIARIA = 5000;
    private final static long MILLISECONDS_IN_DAY = 24 * 60 * 60 * 1000;

    private final Map<Long, Cliente> clientes;
    private final Map<Integer, Item> items;
    private final Map<Integer, ItemRentado> itemsRentados;
    private final Map<Integer, TipoItem> tiposItem;

    private final Map<Integer,Long> mapaPrestamosPorIdCliente;

    public ServiciosAlquilerItemsStub() {
        clientes = new HashMap<>();
        items = new HashMap<>();
        itemsRentados = new HashMap<>();
        tiposItem = new HashMap<>();
        mapaPrestamosPorIdCliente = new HashMap<>();
        poblar();
    }

    @Override
    public long valorMultaRetrasoPorDia(int itemId) throws RentalServicesException {
        Item item = consultarItem(itemId);
        return item.getTarifaxDia();
    }

    @Override
    public Cliente consultarCliente(long docu) throws RentalServicesException {
        Cliente c = null;
        if (clientes.containsKey(docu)) {
            c = clientes.get(docu);
        }
        return c;
    }

    @Override
    public List<ItemRentado> consultarItemsCliente(long idCliente) throws RentalServicesException {
        if (clientes.containsKey(idCliente)) {
            Cliente c = clientes.get(idCliente);
            return c.getRentados();
        } else {
            throw new RentalServicesException("Cliente no registrado: " + idCliente + ".");
        }
    }

    @Override
    public List<Cliente> consultarClientes() throws RentalServicesException {
        return new LinkedList<>(clientes.values());
    }

    @Override
    public Item consultarItem(int id) throws RentalServicesException {
        Item i = null;
        if (items.containsKey(id)) {
            i = items.get(id);
            return i;
        } else {
            throw new RentalServicesException("Ítem no registrado: " + id + ".");
        }
    }

    @Override
    public List<Item> consultarItemsDisponibles() {
        return new LinkedList<>(items.values());
    }

    @Override
    public long consultarMultaAlquiler(int idItem, Date returnDate) throws RentalServicesException {
        if (!itemsRentados.containsKey(idItem)){
            throw new RentalServicesException("El item " + idItem + "no está en alquiler.");
        }
        else{
            ItemRentado ir = itemsRentados.get(idItem);

            LocalDate fechaMinEntrega = ir.getFechaFinRenta().toLocalDate();
            LocalDate fechaEntrega = returnDate.toLocalDate();
            long diasRetraso = ChronoUnit.DAYS.between(fechaMinEntrega, fechaEntrega);
            return diasRetraso * MULTA_DIARIA;
        }
    }

    @Override
    public TipoItem consultarTipoItem(int id) throws RentalServicesException {
        TipoItem i = null;
        if (tiposItem.containsKey(id)) {
            i = tiposItem.get(id);
        }
        return i;
    }

    @Override
    public List<TipoItem> consultarTiposItem() throws RentalServicesException {
        return new LinkedList<>(tiposItem.values());
    }

    @Override
    public void registrarAlquilerCliente(Date date, long docu, Item item, int numDias) throws RentalServicesException {
        LocalDate date1 = date.toLocalDate();
        LocalDate date2 = date1.plusDays(numDias);

        ItemRentado itemRentado = new ItemRentado(0, item, date, Date.valueOf(date2));

        if (clientes.containsKey(docu)) {
            Cliente cliente = clientes.get(docu);
            cliente.getRentados().add(itemRentado);
            items.remove(itemRentado.getItem().getId());
            itemsRentados.put(item.getId(), itemRentado);
            mapaPrestamosPorIdCliente.put(item.getId(), docu);
        } else {
            throw new RentalServicesException("No existe el cliente con el documento " + docu + ".");
        }
    }

    @Override
    public void registrarAlquilerClienteConId(int id, Date date, long docu, Item item, int numDias) throws RentalServicesException {

    }

    @Override
    public void registrarCliente(Cliente p) throws RentalServicesException {
        if (!clientes.containsKey(p.getDocumento())) {
            clientes.put(p.getDocumento(), p);
        } else {
            throw new RentalServicesException("El cliente con documento " + p + " ya está registrado.");
        }
    }

    @Override
    public long consultarCostoAlquiler(int idItem, int numDias) throws RentalServicesException {
        if (!items.containsKey(idItem)) {
            throw new RentalServicesException("El item " + idItem + " no esta disponible.");
        } else {
            return items.get(idItem).getTarifaxDia() * numDias;
        }
    }

    @Override
    public void actualizarTarifaItem(int id, long tarifa) throws RentalServicesException {
        if (!items.containsKey(id)) {
            Item c = items.get(id);
            c.setTarifaxDia(tarifa);
            items.put(id, c);
        } else {
            throw new RentalServicesException("El ítem " + id + " no está registrado.");
        }
    }

    @Override
    public void registrarItem(Item i) throws RentalServicesException {
        if (!items.containsKey(i.getId())) {
            items.put(i.getId(), i);
        } else {
            throw new RentalServicesException("El item " + i.getId() + " ya esta registrado.");
        }
    }

    @Override
    public void vetarCliente(long docu, boolean estado) throws RentalServicesException {
        if (clientes.containsKey(docu)) {
            Cliente c = clientes.get(docu);
            c.setVetado(estado);
        } else {
            throw new RentalServicesException("Cliente no registrado: " + docu);
        }
    }

    @Override
    public List<Item> consultarItems() throws RentalServicesException {
        return new LinkedList<>(items.values());
    }

    @Override
    public void registrarTipoItem(TipoItem tipoItem) {
        tiposItem.put(tiposItem.size() + 1, tipoItem);
    }

    @Override
    public List<ItemRentado> consultarItemsRentados() throws RentalServicesException {
        return new LinkedList<>(itemsRentados.values());
    }

    @Override
    public void limpiarItemsRentados() throws RentalServicesException {
        itemsRentados.clear();
    }

    @Override
    public void limpiarClientes() throws RentalServicesException {
        clientes.clear();
    }

    @Override
    public void limpiarItems() throws RentalServicesException {
        items.clear();
    }

    @Override
    public void limpiarTiposItem() throws RentalServicesException {
        tiposItem.clear();
    }

    @Override
    public ItemRentado consultarItemRentado(int idItem) throws RentalServicesException {
        for (ItemRentado itemRentado: itemsRentados.values()) {
            if (itemRentado.getItem().getId() == idItem) {
                return itemRentado;
            }
        }
        throw new RentalServicesException("Error al consultar el ítem rentado.");
    }

    private void poblar() {
        TipoItem tipoItem1 = new TipoItem(1,"Vídeo");
        TipoItem tipoItem2 = new TipoItem(2,"Juego");
        TipoItem tipoItem3 = new TipoItem(3,"Música");
        tiposItem.put(1, tipoItem1);
        tiposItem.put(2, tipoItem2);
        tiposItem.put(3, tipoItem3);

        Item item1 = new Item(tipoItem1, 1, "Los 4 Fantásticos", "Los 4 Fantásticos  es una película de superhéroes  basada en la serie de cómic homónima de Marvel.", java.sql.Date.valueOf("2005-06-08"), 2000, "DVD", "Ciencia Ficcion");
        Item item2 = new Item(tipoItem2, 2, "Halo 3", "Halo 3 es un videojuego de disparos en primera persona desarrollado por Bungie Studios.", java.sql.Date.valueOf("2007-09-08"), 3000, "DVD", "Shooter");
        Item item3 = new Item(tipoItem3, 3, "Thriller", "Thriller es una canción interpretada por el cantante estadounidense Michael Jackson, compuesta por Rod Temperton y producida por Quincy Jones.", java.sql.Date.valueOf("1984-01-11"), 2000, "DVD", "Pop");
        Item item4 = new Item(tipoItem1, 4, "Los 4 Fantásticos", "Los 4 Fantásticos  es una película de superhéroes  basada en la serie de cómic homónima de Marvel.", java.sql.Date.valueOf("2005-06-08"), 2000, "DVD", "Ciencia Ficcion");
        Item item5 = new Item(tipoItem2, 5, "Halo 3", "Halo 3 es un videojuego de disparos en primera persona desarrollado por Bungie Studios.", java.sql.Date.valueOf("2007-09-08"), 3000, "DVD", "Shooter");
        Item item6 = new Item(tipoItem3, 6, "Thriller", "Thriller es una canción interpretada por el cantante estadounidense Michael Jackson, compuesta por Rod Temperton y producida por Quincy Jones.", java.sql.Date.valueOf("1984-01-11"), 2000, "DVD", "Pop");
        items.put(1, item1);
        items.put(2, item2);
        items.put(3, item3);
        items.put(4, item4);
        items.put(5, item5);
        items.put(6, item6);

        ItemRentado ir1 = new ItemRentado(0, item1, java.sql.Date.valueOf("2017-01-01"), java.sql.Date.valueOf("2017-03-12"));
        ItemRentado ir2 = new ItemRentado(0, item2, java.sql.Date.valueOf("2017-01-04"), java.sql.Date.valueOf("2017-04-7"));
        ItemRentado ir3 = new ItemRentado(0, item3, java.sql.Date.valueOf("2017-01-07"), java.sql.Date.valueOf("2017-07-12"));
        ArrayList<ItemRentado> list1 = new ArrayList<>();
        list1.add(ir1);
        ArrayList<ItemRentado> list2 = new ArrayList<>();
        list2.add(ir2);
        ArrayList<ItemRentado> list3 = new ArrayList<>();
        list3.add(ir3);

        Cliente c1 = new Cliente("Oscar Alba", 1026585664, "6788952", "KRA 109#34-C30", "oscar@hotmail.com", false,list1);
        Cliente c2 = new Cliente("Carlos Ramirez", 1026585663, "6584562", "KRA 59#27-a22", "carlos@hotmail.com", false,list2);
        Cliente c3 = new Cliente("Ricardo Pinto", 1026585669, "4457863", "KRA 103#94-a77", "ricardo@hotmail.com", false,list3);
        clientes.put(c1.getDocumento(), c1);
        clientes.put(c2.getDocumento(), c2);
        clientes.put(c3.getDocumento(), c3);
    }
}
