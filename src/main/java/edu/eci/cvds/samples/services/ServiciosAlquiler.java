package edu.eci.cvds.samples.services;

import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.samples.entities.ItemRentado;
import edu.eci.cvds.samples.entities.TipoItem;

import java.sql.Date;
import java.util.List;

public interface ServiciosAlquiler {

    long valorMultaRetrasoPorDia(int itemId) throws RentalServicesException;

    Cliente consultarCliente(long docu) throws RentalServicesException;

    /**
     * @obj Consultar los items que tenga en su poder un cliente.
     * @param idCliente Identificador del cliente.
     * @return El listado de detalle de los ítems rentados por el cliente.
     * @throws RentalServicesException Si el cliente no está registrado.
     */
    List<ItemRentado> consultarItemsCliente(long idCliente) throws RentalServicesException;

    List<Cliente> consultarClientes() throws RentalServicesException;

    Item consultarItem(int id) throws RentalServicesException;

    /**
     * @obj Consultar los ítems que están disponibles para alquiler.
     * @return El listado de ítems disponibles.
     */
    List<Item> consultarItemsDisponibles() throws RentalServicesException;

    /**
     * @obj Consultar el valor de la multa del alquiler, dado el id del ítem
     * alquilado hasta la fecha dada como parámetro.
     * @param idItem El código del item alquilado.
     * @param returnDate La fecha de devolución del ítem.
     * @return La multa en función del número de dias de retraso. Si el ítem se
     * entrega en la fecha exacta de entrega, o antes, la multa será cero.
     * @throws RentalServicesException Si el ítem no existe o no está
     * actualmente alquilado.
     */
    long consultarMultaAlquiler(int idItem, Date returnDate) throws RentalServicesException;

    TipoItem consultarTipoItem(int id) throws RentalServicesException;

    List<TipoItem> consultarTiposItem() throws RentalServicesException;

    /**
     * @obj Registrar el alquiler de un item.
     * @pre numDias >= 1
     * @param date Fecha de registro del alquiler.
     * @param docu Identificación de a quién se le cargará el alquiler.
     * @param item El identificador del ítem a alquilar.
     * @param numDias El número de dias que se le prestará el ítem.
     * @pos El ítem ya no debe estar disponible, y debe estar asignado al
     * cliente.
     * @throws RentalServicesException Si el identificador no corresponde con un ítem, o si
     * el mismo ya está alquilado.
     */
    void registrarAlquilerCliente(Date date, long docu, Item item, int numDias) throws RentalServicesException;

    void registrarAlquilerClienteConId(int id, Date date, long docu, Item item, int numDias) throws RentalServicesException;

    void registrarCliente(Cliente p) throws RentalServicesException;

    /**
     * @obj Consultar el costo del alquiler de un item.
     * @pre numDias >= 1
     * @param idItem El código del ítem.
     * @param numDias El número de dias que se va a alquilar.
     * @return El costo total del alquiler, teniendo en cuesta el costo diario y
     * el número de días del alquiler.
     * @throws RentalServicesException Si el identificador del ítem no existe.
     */
    long consultarCostoAlquiler(int idItem, int numDias) throws RentalServicesException;

    void actualizarTarifaItem(int id, long tarifa) throws RentalServicesException;

    void registrarItem(Item i) throws RentalServicesException;

    void vetarCliente(long docu, boolean estado) throws RentalServicesException;

    List<Item> consultarItems() throws RentalServicesException;

    void registrarTipoItem(TipoItem tipoItem) throws RentalServicesException;

    List<ItemRentado> consultarItemsRentados() throws RentalServicesException;

    void limpiarItemsRentados() throws RentalServicesException;

    void limpiarClientes() throws RentalServicesException;

    void limpiarItems() throws RentalServicesException;

    void limpiarTiposItem() throws RentalServicesException;

    ItemRentado consultarItemRentado(int idItem) throws RentalServicesException;
}
