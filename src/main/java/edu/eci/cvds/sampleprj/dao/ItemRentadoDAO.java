package edu.eci.cvds.sampleprj.dao;

import edu.eci.cvds.samples.entities.ItemRentado;
import org.apache.ibatis.exceptions.PersistenceException;

import java.util.List;

public interface ItemRentadoDAO {

    ItemRentado consultarItemRentado(int id) throws PersistenceException;

    List<ItemRentado> consultarItemsRentados() throws PersistenceException;

    void limpiarItemsRentados() throws PersistenceException;
}
