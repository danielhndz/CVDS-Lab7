package edu.eci.cvds.sampleprj.dao.mybatis.mappers;


import edu.eci.cvds.samples.entities.Item;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * @author 2106913
 */
public interface ItemMapper {
    
    List<Item> consultarItems();
    
    Item consultarItem(@Param("id") int id);
    
    void insertarItem(@Param("item") Item it);
}
