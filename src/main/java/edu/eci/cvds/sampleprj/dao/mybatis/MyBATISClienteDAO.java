package edu.eci.cvds.sampleprj.dao.mybatis;

import com.google.inject.Inject;
import edu.eci.cvds.sampleprj.dao.ClienteDAO;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.ClienteMapper;
import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.ItemRentado;
import org.apache.ibatis.exceptions.PersistenceException;

import java.sql.Date;
import java.util.List;

public class MyBATISClienteDAO implements ClienteDAO {

    @Inject
    ClienteMapper clienteMapper;

    @Override
    public Cliente loadById(long id) throws PersistenceException {
        try {
            return clienteMapper.consultarCliente(id);
        } catch (PersistenceException e) {
            throw new PersistenceException("Error al consultar cliente", e);
        }
    }

    @Override
    public void addRentedItemToClient(long clientId, int itemId, Date startDate, Date finalDate) throws PersistenceException {
        try {
            clienteMapper.agregarItemRentadoACliente(clientId, itemId, startDate, finalDate);
        } catch (PersistenceException e) {
            throw new PersistenceException("Error al agregar el ítem " + itemId + " al cliente " + clientId + ".", e);
        }
    }

    @Override
    public void addRentedItemToClientWithId(int id, long clientId, int itemId, Date startDate, Date finalDate) throws PersistenceException {
        try {
            clienteMapper.agregarItemRentadoAClienteConId(id, clientId, itemId, startDate, finalDate);
        } catch (PersistenceException e) {
            throw new PersistenceException("Error al agregar el ítem " + itemId + " al cliente " + clientId + ".", e);
        }
    }

    @Override
    public List<Cliente> loadAll() throws PersistenceException {
        try {
            return clienteMapper.consultarClientes();
        } catch (PersistenceException e) {
            throw new PersistenceException("Error al consultar los clientes.", e);
        }
    }

    @Override
    public List<ItemRentado> loadRentedItemsByIdClient(long docu) throws PersistenceException {
        try {
            return clienteMapper.consultarItemsCliente(docu);
        } catch (PersistenceException e) {
            throw new PersistenceException("Error al cargar los ítems rentados por el cliente " + docu + ".", e);
        }
    }

    @Override
    public void registerClient(Cliente cliente) throws PersistenceException {
        try {
            clienteMapper.registrarCliente(cliente);
        } catch (PersistenceException e) {
            e.printStackTrace();
            throw new PersistenceException("Error al registrar el cliente " + cliente.getDocumento() + ".", e);
        }
    }

    @Override
    public void vetarCliente(long docu, boolean estado) throws PersistenceException {
        try {
            clienteMapper.vetarCliente(docu, estado);
        } catch (PersistenceException e) {
            throw new PersistenceException("Error al vetar al cliente " + docu + ".", e);
        }
    }

    @Override
    public void limpiarClientes() throws PersistenceException {
        try {
            clienteMapper.limpiarClientes();
        } catch (PersistenceException e) {
            throw new PersistenceException("Error al limpiar clientes.", e);
        }
    }
}
