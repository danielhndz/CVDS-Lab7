package edu.eci.cvds.samples.services.impl;

import com.google.inject.Inject;
import edu.eci.cvds.sampleprj.dao.ClienteDAO;
import edu.eci.cvds.sampleprj.dao.ItemDAO;
import edu.eci.cvds.sampleprj.dao.ItemRentadoDAO;
import edu.eci.cvds.sampleprj.dao.TipoItemDAO;
import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.samples.entities.ItemRentado;
import edu.eci.cvds.samples.entities.TipoItem;
import edu.eci.cvds.samples.services.RentalServicesException;
import edu.eci.cvds.samples.services.ServiciosAlquiler;
import org.apache.ibatis.exceptions.PersistenceException;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ServiciosAlquilerImpl implements ServiciosAlquiler {

    private final static long MILLISECONDS_IN_DAY = 24 * 60 * 60 * 1000;

    @Inject
    ClienteDAO clienteDAO;

    @Inject
    ItemDAO itemDAO;

    @Inject
    ItemRentadoDAO itemRentadoDAO;

    @Inject
    TipoItemDAO tipoItemDAO;

    @Override
    public long valorMultaRetrasoPorDia(int itemId) throws RentalServicesException {
        try {
            return itemDAO.valorMultaRetrasoPorDia(itemId);
        } catch (PersistenceException e) {
            throw new RentalServicesException(RentalServicesException.ID_ITEM_NO_EXISTE, e);
        }
    }

    @Override
    public Cliente consultarCliente(long docu) throws RentalServicesException {
        try {
            return clienteDAO.loadById(docu);
        } catch (PersistenceException e) {
            throw new RentalServicesException(RentalServicesException.CLIENTE_NO_REGISTRADO, e);
        }
    }

    @Override
    public List<ItemRentado> consultarItemsCliente(long idCliente) throws RentalServicesException {
        try {
            return clienteDAO.loadRentedItemsByIdClient(idCliente);
        } catch (PersistenceException e) {
            throw new RentalServicesException(RentalServicesException.CLIENTE_NO_REGISTRADO, e);
        }
    }

    @Override
    public List<Cliente> consultarClientes() throws RentalServicesException {
        try {
            return clienteDAO.loadAll();
        } catch (PersistenceException e) {
            throw new RentalServicesException(RentalServicesException.ERROR_CLIENTES, e);
        }
    }

    @Override
    public Item consultarItem(int id) throws RentalServicesException {
        try {
            return itemDAO.load(id);
        } catch (PersistenceException e) {
            throw new RentalServicesException(RentalServicesException.ITEM_NO_REGISTRADO, e);
        }
    }

    @Override
    public List<Item> consultarItemsDisponibles() throws RentalServicesException {
        try {
            return itemDAO.loadAvailable();
        } catch (PersistenceException e) {
            throw new RentalServicesException(RentalServicesException.ERROR_ITEMS_DISPONIBLES, e);
        }
    }

    @Override
    public long consultarMultaAlquiler(int idItem, Date returnDate) throws RentalServicesException {
        try {
            long multaPorDia = valorMultaRetrasoPorDia(idItem);
            ItemRentado itemRentado = itemRentadoDAO.consultarItemRentado(idItem);

            LocalDate fechaMinEntrega = itemRentado.getFechaFinRenta().toLocalDate();
            LocalDate fechaEntrega = returnDate.toLocalDate();
            long diasRetraso = ChronoUnit.DAYS.between(fechaMinEntrega, fechaEntrega);
            return multaPorDia * diasRetraso;
        } catch (PersistenceException e) {
            throw new RentalServicesException(RentalServicesException.ITEM_NO_EXISTE_O_NO_ALQUILADO, e);
        }
    }

    @Override
    public TipoItem consultarTipoItem(int id) throws RentalServicesException {
        try {
            return tipoItemDAO.loadById(id);
        } catch (PersistenceException e) {
            throw new RentalServicesException(RentalServicesException.TIPO_ITEM_NO_REGISTRADO, e);
        }
    }

    @Override
    public List<TipoItem> consultarTiposItem() throws RentalServicesException {
        try {
            return tipoItemDAO.loadAll();
        } catch (PersistenceException e) {
            throw new RentalServicesException(RentalServicesException.ERROR_TODOS_TIPO_ITEM, e);
        }
    }

    @Override
    public void registrarAlquilerCliente(Date date, long docu, Item item, int numDias) throws RentalServicesException {
        try {
            LocalDate date1 = date.toLocalDate();
            LocalDate date2 = date1.plusDays(numDias);

            Date finalDate = Date.valueOf(date2);

            clienteDAO.addRentedItemToClient(docu, item.getId(), date, finalDate);
        } catch (PersistenceException e) {
            throw new RentalServicesException(RentalServicesException.ITEM_NO_EXISTE_O_NO_ALQUILADO, e);
        }
    }

    @Override
    public void registrarAlquilerClienteConId(int id, Date date, long docu, Item item, int numDias) throws RentalServicesException {
        try {
            LocalDate date1 = date.toLocalDate();
            LocalDate date2 = date1.plusDays(numDias);

            Date finalDate = Date.valueOf(date2);

            clienteDAO.addRentedItemToClientWithId(id, docu, item.getId(), date, finalDate);
        } catch (PersistenceException e) {
            throw new RentalServicesException(RentalServicesException.ITEM_NO_EXISTE_O_NO_ALQUILADO, e);
        }
    }

    @Override
    public void registrarCliente(Cliente cliente) throws RentalServicesException {
        try {
            clienteDAO.registerClient(cliente);
        } catch (PersistenceException e) {
            throw new RentalServicesException(RentalServicesException.ERROR_AL_REGISTRAR_CLIENTE, e);
        }
    }

    @Override
    public long consultarCostoAlquiler(int idItem, int numDias) throws RentalServicesException {
        try {
            long costoPorDia = valorMultaRetrasoPorDia(idItem);
            return costoPorDia * numDias;
        } catch (PersistenceException e) {
            throw new RentalServicesException(RentalServicesException.ID_ITEM_NO_EXISTE, e);
        }
    }

    @Override
    public void actualizarTarifaItem(int id, long tarifa) throws RentalServicesException {
        try {
            itemDAO.actualizarTarifaItem(id, tarifa);
        } catch (PersistenceException e) {
            throw new RentalServicesException(RentalServicesException.ID_ITEM_NO_EXISTE, e);
        }
    }

    @Override
    public void registrarItem(Item i) throws RentalServicesException {
        try {
            itemDAO.save(i);
        } catch (PersistenceException e) {
            throw new RentalServicesException(RentalServicesException.ERROR_AL_REGISTRAR_ITEM, e);
        }
    }

    @Override
    public void vetarCliente(long docu, boolean estado) throws RentalServicesException {
        try {
            clienteDAO.vetarCliente(docu, estado);
        } catch (PersistenceException e) {
            throw new RentalServicesException(RentalServicesException.CLIENTE_NO_REGISTRADO, e);
        }
    }

    @Override
    public List<Item> consultarItems() throws RentalServicesException {
        try {
            return itemDAO.loadAll();
        } catch (PersistenceException e) {
            throw new RentalServicesException("Error al cargar todos los ítems.");
        }
    }

    @Override
    public void registrarTipoItem(TipoItem tipoItem) throws RentalServicesException {
        try {
            tipoItemDAO.add(tipoItem);
        } catch (PersistenceException e) {
            throw new RentalServicesException("Error al registrar el tipo de ítem " + tipoItem.getID() + ".");
        }
    }

    @Override
    public List<ItemRentado> consultarItemsRentados() throws RentalServicesException {
        try {
            return itemRentadoDAO.consultarItemsRentados();
        } catch (PersistenceException e) {
            throw new RentalServicesException("Error al consultar los ítems rentados.", e);
        }
    }

    @Override
    public void limpiarItemsRentados() throws RentalServicesException {
        try {
            itemRentadoDAO.limpiarItemsRentados();
        } catch (PersistenceException e) {
            throw new RentalServicesException("Error al limpiar los ítems rentados.", e);
        }
    }

    @Override
    public void limpiarClientes() throws RentalServicesException {
        try {
            clienteDAO.limpiarClientes();
        } catch (PersistenceException e) {
            throw new RentalServicesException("Error al limpiar cliente.", e);
        }
    }

    @Override
    public void limpiarItems() throws RentalServicesException {
        try {
            itemDAO.limpiarItems();
        } catch (PersistenceException e) {
            throw new RentalServicesException("Error al limpiar ítems.", e);
        }
    }

    @Override
    public void limpiarTiposItem() throws RentalServicesException {
        try {
            tipoItemDAO.limpiarTiposItem();
        } catch (PersistenceException e) {
            throw new RentalServicesException("Error al limpiar los tipos de ítem.", e);
        }
    }

    @Override
    public ItemRentado consultarItemRentado(int idItem) throws RentalServicesException {
        try {
            return itemRentadoDAO.consultarItemRentado(idItem);
        } catch (PersistenceException e) {
            throw new RentalServicesException("Error al consultar el ítem rentado.", e);
        }
    }
}
