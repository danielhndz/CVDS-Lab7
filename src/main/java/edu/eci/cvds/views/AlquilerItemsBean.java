package edu.eci.cvds.views;

import com.google.inject.Inject;
import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.samples.entities.ItemRentado;
import edu.eci.cvds.samples.services.RentalServicesException;
import edu.eci.cvds.samples.services.ServiciosAlquiler;
import org.apache.ibatis.exceptions.PersistenceException;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@SuppressWarnings({"deprecation", "unused"})
@ManagedBean(name = "alquilerItemsBean", eager = true)
@ApplicationScoped
public class AlquilerItemsBean extends BasePageBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private ServiciosAlquiler serviciosAlquiler;

    private Cliente selectedCliente;
    private long costo;

    public void registrarCliente(String nombre, long documento, String telefono, String direccion, String email) throws RentalServicesException {
        try{
            Cliente cliente = new Cliente(nombre, documento, telefono, direccion, email);
            serviciosAlquiler.registrarCliente(cliente);
        } catch (RentalServicesException e) {
            throw new RentalServicesException("Error al registrar el cliente " + documento + ".", e);
        }
    }

    public Cliente getSelectedCliente() {
        return selectedCliente;
    }

    public void setSelectedCliente(Cliente cliente) {
        this.selectedCliente = cliente;
    }

    public List<Cliente> consultarClientes() throws RentalServicesException {
        try{
            return serviciosAlquiler.consultarClientes();
        } catch (RentalServicesException e) {
            throw new RentalServicesException("Error al consultar clientes.", e);
        }
    }

    public List<ItemRentado> consultarItemsCliente(long idCliente) throws RentalServicesException {
        try {
            return serviciosAlquiler.consultarItemsCliente(idCliente);
        } catch (RentalServicesException e) {
            throw new RentalServicesException("Error al consultar los ítems rentados por el cliente " + idCliente + ".", e);
        }
    }

    public void registrarAlquilerCliente(int iDItem, int numDias) throws RentalServicesException {
        try {
            Item item = serviciosAlquiler.consultarItem(iDItem);
            serviciosAlquiler.registrarAlquilerCliente(new Date(System.currentTimeMillis()), selectedCliente.getDocumento(), item, numDias);
        } catch (RentalServicesException e) {
            throw new RentalServicesException("Error al registrar el alquiler del cliente.", e);
        }
    }

    public long consultarMultaAlquiler(int idItem) throws RentalServicesException {
        try {
            long multaPorDia = serviciosAlquiler.valorMultaRetrasoPorDia(idItem);
            ItemRentado itemRentado = serviciosAlquiler.consultarItemRentado(idItem);

            LocalDate fechaMinEntrega = itemRentado.getFechaFinRenta().toLocalDate();
            LocalDate fechaEntrega = new Date(System.currentTimeMillis()).toLocalDate();
            long diasRetraso = ChronoUnit.DAYS.between(fechaMinEntrega, fechaEntrega);
            return multaPorDia * diasRetraso;
        } catch (PersistenceException e) {
            throw new RentalServicesException(RentalServicesException.ITEM_NO_EXISTE_O_NO_ALQUILADO, e);
        }
    }

    public void consultarCosto(int idItem , int numDias) throws RentalServicesException {
        try {
            this.costo = serviciosAlquiler.consultarCostoAlquiler(idItem, numDias);
        } catch (RentalServicesException e) {
            throw new RentalServicesException("Error al consultar costo alquiler.", e);
        }
    }

    public long getCosto(){
        return costo;
    }
}
