package edu.eci.cvds.sampleprj.dao.mybatis.mappers;

import java.util.Date;
import java.util.List;

import edu.eci.cvds.samples.entities.ItemRentado;
import edu.eci.cvds.samples.services.RentalServicesException;
import org.apache.ibatis.annotations.Param;

import edu.eci.cvds.samples.entities.Cliente;
import org.mybatis.guice.transactional.Transactional;

/**
 *
 * @author Daniel Hernández
 */
public interface ClienteMapper {

    /**
     * Consultar un cliente específico por su número de documento.
     * @param docu Número de documento del cliente.
     * @return El cliente consultado.
     */
    Cliente consultarCliente(@Param("docu") long docu);
    
    /**
     * Registrar un nuevo item rentado asociado al cliente identificado
     * con 'idc' y relacionado con el item identificado con 'idi'.
     * @param idCli Id del cliente.
     * @param idItm Id del ítem.
     * @param fechaInicio Fecha del inicio del alquiler.
     * @param fechaFin Fecha del fin del alquiler.
     */
    @Transactional
    void agregarItemRentadoACliente(@Param("idCli") long idCli,
                                    @Param("idItm") int idItm,
                                    @Param("fechaInicio") Date fechaInicio,
                                    @Param("fechaFin") Date fechaFin);

    @Transactional
    void agregarItemRentadoAClienteConId(@Param("id") int id,
                                         @Param("idCli") long idCli,
                                         @Param("idItm") int idItm,
                                         @Param("fechaInicio") Date fechaInicio,
                                         @Param("fechaFin") Date fechaFin);

    /**
     * Consultar todos los clientes.
     * @return Lista de todos los cliente.
     */
    List<Cliente> consultarClientes();

    /**
     * Consultar los ítems rentados por un cliente.
     * @param docu Número de documento del cliente.
     * @return Lista de los ítems rentados por el cliente.
     */
    List<ItemRentado> consultarItemsCliente(@Param("docu") long docu);

    @Transactional
    void registrarCliente(@Param("cliente") Cliente cliente);

    @Transactional
    void vetarCliente(@Param("docu") long docu, @Param("estado") boolean estado);

    @Transactional
    void limpiarClientes();
}
