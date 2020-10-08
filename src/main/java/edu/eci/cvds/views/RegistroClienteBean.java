package edu.eci.cvds.views;

import com.google.inject.Inject;
import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.samples.entities.ItemRentado;
import edu.eci.cvds.samples.services.RentalServicesException;
import edu.eci.cvds.samples.services.ServiciosAlquiler;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@SuppressWarnings({"deprecation", "unused"})
@ManagedBean(name = "clientBean", eager = true)
@ViewScoped
public class RegistroAlquilerBean extends BasePageBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private ServiciosAlquiler serviciosAlquiler;

    private Cliente selectedCliente;
    private long costo;

    private String nombre;
    private long documento;
    private String telefono;
    private String direccion;
    private String email;

    public void registrarCliente() throws RentalServicesException {
        try{
            serviciosAlquiler.registrarCliente(
                    new Cliente(nombre, documento, telefono, direccion, email)
            );
            limpiarCampos();
        } catch (RentalServicesException e) {
            throw new RentalServicesException(e.getMessage(), e);
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
            throw new RentalServicesException(e.getMessage(), e);
        }
    }

    public List<ItemRentado> consultarItemsCliente(long idCliente) throws RentalServicesException {
        try {
            return serviciosAlquiler.consultarItemsCliente(idCliente);
        } catch (RentalServicesException e) {
            throw new RentalServicesException(e.getMessage(), e);
        }
    }

    public void registrarAlquilerCliente(int iDItem, int numDias) throws RentalServicesException {
        try {
            Item item = serviciosAlquiler.consultarItem(iDItem);
            serviciosAlquiler.registrarAlquilerCliente(new Date(System.currentTimeMillis()), selectedCliente.getDocumento(), item, numDias);
        } catch (RentalServicesException e) {
            throw new RentalServicesException(e.getMessage(), e);
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
        } catch (RentalServicesException e) {
            throw new RentalServicesException(e.getMessage(), e);
        }
    }

    public void consultarCosto(int idItem , int numDias) throws RentalServicesException {
        try {
            this.costo = serviciosAlquiler.consultarCostoAlquiler(idItem, numDias);
        } catch (RentalServicesException e) {
            throw new RentalServicesException(e.getMessage(), e);
        }
    }

    public long getCosto(){
        return costo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getDocumento() {
        return documento;
    }

    public void setDocumento(long documento) {
        this.documento = documento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private void limpiarCampos() {
        setNombre("");
        setDocumento(0);
        setEmail("");
        setDireccion("");
        setTelefono("");
    }
}
