package edu.eci.cvds.sampleprj.dao;

import edu.eci.cvds.samples.entities.Item;
import org.apache.ibatis.exceptions.PersistenceException;

import java.util.List;

public interface ItemDAO {

    int valorMultaRetrasoPorDia(int id) throws PersistenceException;

    void save(Item item) throws PersistenceException;

    Item load(int id) throws PersistenceException;

    List<Item> loadAll() throws PersistenceException;

    List<Item> loadAvailable() throws PersistenceException;

    void actualizarTarifaItem(int id, long tarifa);

    void limpiarItems();
}
