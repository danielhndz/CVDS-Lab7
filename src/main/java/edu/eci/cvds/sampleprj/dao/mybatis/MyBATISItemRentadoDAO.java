package edu.eci.cvds.sampleprj.dao.mybatis;

import com.google.inject.Inject;
import edu.eci.cvds.sampleprj.dao.ItemRentadoDAO;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.ItemMapper;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.ItemRentadoMapper;
import edu.eci.cvds.samples.entities.ItemRentado;
import org.apache.ibatis.exceptions.PersistenceException;

import java.util.List;

public class MyBATISItemRentadoDAO implements ItemRentadoDAO {

    @Inject
    ItemRentadoMapper itemRentadoMapper;

    @Override
    public ItemRentado consultarItemRentado(int id) throws PersistenceException {
        try {
            return itemRentadoMapper.consultarItemRentado(id);
        } catch (PersistenceException e) {
            throw new PersistenceException("Error al consultar el ítem rentado " + id + ".", e);
        }
    }

    @Override
    public List<ItemRentado> consultarItemsRentados() throws PersistenceException {
        try {
            return itemRentadoMapper.consultarItemsRentados();
        } catch (PersistenceException e) {
            throw new PersistenceException("Error al consultar los ítems rentados", e);
        }
    }

    @Override
    public void limpiarItemsRentados() throws PersistenceException {
        try {
            itemRentadoMapper.limpiarItemsRentados();
        } catch (PersistenceException e) {
            throw new PersistenceException("Error al limpiar los ítems rentados", e);
        }
    }
}
