package edu.eci.cvds.sampleprj.dao.mybatis.mappers;


import edu.eci.cvds.samples.entities.TipoItem;

import java.util.List;

@SuppressWarnings("unused")
public interface TipoItemMapper {
    
    List<TipoItem> getTiposItems();
    
    TipoItem getTipoItem(int id);
    
    void addTipoItem(String des);
}
