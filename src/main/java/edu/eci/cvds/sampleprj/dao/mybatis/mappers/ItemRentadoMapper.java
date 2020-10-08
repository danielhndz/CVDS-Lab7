package edu.eci.cvds.sampleprj.dao.mybatis.mappers;

import edu.eci.cvds.samples.entities.ItemRentado;
import org.apache.ibatis.annotations.Param;
import org.mybatis.guice.transactional.Transactional;

import java.util.List;

public interface ItemRentadoMapper {

    ItemRentado consultarItemRentado(@Param("itemId") int itemId);

    List<ItemRentado> consultarItemsRentados();

    @Transactional
    void limpiarItemsRentados();
}
