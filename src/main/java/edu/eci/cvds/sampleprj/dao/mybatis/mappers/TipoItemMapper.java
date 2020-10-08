package edu.eci.cvds.sampleprj.dao.mybatis.mappers;


import edu.eci.cvds.samples.entities.TipoItem;
import org.apache.ibatis.annotations.Param;
import org.mybatis.guice.transactional.Transactional;

import java.util.List;


public interface TipoItemMapper {
    
    List<TipoItem> getTiposItems();
    
    TipoItem getTipoItem(@Param("tipoItemId") int tipoItemId);

    @Transactional
    void addTipoItem(@Param("tipoItem") TipoItem tipoItem);

    @Transactional
    void limpiarTiposItem();
}
