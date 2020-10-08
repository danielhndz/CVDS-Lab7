package edu.eci.cvds.sampleprj.dao.mybatis;

import com.google.inject.Inject;
import edu.eci.cvds.sampleprj.dao.TipoItemDAO;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.TipoItemMapper;
import edu.eci.cvds.samples.entities.TipoItem;
import edu.eci.cvds.samples.services.RentalServicesException;
import org.apache.ibatis.exceptions.PersistenceException;

import java.util.List;

public class MyBATISTipoItemDAO implements TipoItemDAO {

    @Inject
    TipoItemMapper tipoItemMapper;

    @Override
    public List<TipoItem> loadAll() throws PersistenceException {
        try {
            return tipoItemMapper.getTiposItems();
        } catch (PersistenceException e) {
            throw new PersistenceException("Error al consultar los tipos de ítems.", e);
        }
    }

    @Override
    public TipoItem loadById(int id) throws PersistenceException {
        try {
            return tipoItemMapper.getTipoItem(id);
        } catch (PersistenceException e) {
            throw new PersistenceException("Error al consultar el tipo de ítem " + id + ".", e);
        }
    }

    @Override
    public void add(TipoItem tipoItem) throws PersistenceException {
        try {
            tipoItemMapper.addTipoItem(tipoItem);
        } catch (PersistenceException e) {
            throw new PersistenceException("Error al añadir el tipo de ítem.", e);
        }
    }

    @Override
    public void limpiarTiposItem() throws RentalServicesException {
        try {
            tipoItemMapper.limpiarTiposItem();
        } catch (PersistenceException e) {
            throw new PersistenceException("Error al limpiar los tipos de ítem.", e);
        }
    }
}
