package edu.eci.cvds.sampleprj.dao.mybatis;

import com.google.inject.Inject;
import edu.eci.cvds.sampleprj.dao.ItemDAO;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.ItemMapper;
import edu.eci.cvds.samples.entities.Item;
import org.apache.ibatis.exceptions.PersistenceException;

import java.util.List;

public class MyBATISItemDAO implements ItemDAO {

    @Inject
    private ItemMapper itemMapper;

    @Override
    public int valorMultaRetrasoPorDia(int id) throws PersistenceException {
        try {
            return itemMapper.valorMultaRetrasoPorDia(id);
        } catch (PersistenceException e) {
            throw new PersistenceException("Error al consultar la tarifa por día del ítem " + id + ".", e);
        }
    }

    @Override
    public void save(Item item) throws PersistenceException {
        try {
            itemMapper.insertarItem(item);
        } catch (PersistenceException e) {
            throw new PersistenceException("Error al registrar el ítem " + item.toString() + ".", e);
        }
    }

    @Override
    public Item load(int id) throws PersistenceException {
        try {
            return itemMapper.consultarItem(id);
        } catch (PersistenceException e) {
            throw new PersistenceException("Error al consultar el ítem " + id + ".", e);
        }
    }

    @Override
    public List<Item> loadAll() throws PersistenceException {
        try {
            return itemMapper.consultarItems();
        } catch (PersistenceException e) {
            throw new PersistenceException("Error al consultar los ítems" + ".", e);
        }
    }

    @Override
    public List<Item> loadAvailable() throws PersistenceException {
        try {
            return itemMapper.loadAvailable();
        } catch (PersistenceException e) {
            throw new PersistenceException("Error al consultar los ítems disponibles.", e);
        }
    }

    @Override
    public void actualizarTarifaItem(int id, long tarifa) {
        try {
            itemMapper.actualizarTarifaItem(id, tarifa);
        } catch (PersistenceException e) {
            throw new PersistenceException("Error al actualizar la tarifa para el ítem " + id + ".", e);
        }
    }

    @Override
    public void limpiarItems() {
        try {
            itemMapper.limpiarItems();
        } catch (PersistenceException e) {
            throw new PersistenceException("Error al limpiar ítems.", e);
        }
    }
}
