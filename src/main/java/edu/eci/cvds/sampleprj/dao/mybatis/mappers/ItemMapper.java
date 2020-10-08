package edu.eci.cvds.sampleprj.dao.mybatis.mappers;


import edu.eci.cvds.samples.entities.Item;
import org.apache.ibatis.annotations.Param;
import org.mybatis.guice.transactional.Transactional;

import java.util.List;

/**
 *
 * @author 2106913
 */
public interface ItemMapper {

    int valorMultaRetrasoPorDia(@Param("id") int id);
    
    List<Item> consultarItems();
    
    Item consultarItem(@Param("id") int id);

    @Transactional
    void insertarItem(@Param("item") Item item);

    List<Item> loadAvailable();

    @Transactional
    void actualizarTarifaItem(@Param("id") int id, @Param("tarifa") long tarifa);

    @Transactional
    void limpiarItems();
}
