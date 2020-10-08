package edu.eci.cvds.sampleprj.dao;

import edu.eci.cvds.samples.entities.TipoItem;
import edu.eci.cvds.samples.services.RentalServicesException;
import org.apache.ibatis.exceptions.PersistenceException;

import java.util.List;

public interface TipoItemDAO {

    List<TipoItem> loadAll() throws PersistenceException;

    TipoItem loadById(int id) throws PersistenceException;

    void add(TipoItem tipoItem) throws PersistenceException;

    void limpiarTiposItem() throws RentalServicesException;
}
