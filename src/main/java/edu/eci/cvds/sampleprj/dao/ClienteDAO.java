package edu.eci.cvds.sampleprj.dao;

import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.ItemRentado;
import org.apache.ibatis.exceptions.PersistenceException;

import java.sql.Date;
import java.util.List;

public interface ClienteDAO {

    Cliente loadById(long id) throws PersistenceException;

    void addRentedItemToClient(long clientId, int itemId, Date startDate, Date finalDate) throws PersistenceException;

    void addRentedItemToClientWithId(int id, long clientId, int itemId, Date startDate, Date finalDate) throws PersistenceException;

    List<Cliente> loadAll() throws PersistenceException;

    List<ItemRentado> loadRentedItemsByIdClient(long docu) throws PersistenceException;

    void registerClient(Cliente cliente) throws PersistenceException;

    void vetarCliente(long docu, boolean estado) throws PersistenceException;

    void limpiarClientes() throws PersistenceException;
}
