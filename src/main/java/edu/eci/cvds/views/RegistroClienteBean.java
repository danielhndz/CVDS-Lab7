package edu.eci.cvds.views;

import com.google.inject.Inject;
import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.services.RentalServicesException;
import edu.eci.cvds.samples.services.ServiciosAlquiler;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.util.List;

@SuppressWarnings("deprecation")
@ManagedBean(name = "registroClienteBean", eager = true)
@SessionScoped
public class RegistroClienteBean extends BasePageBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private ServiciosAlquiler serviciosAlquiler;

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
            clear();
        } catch (RentalServicesException e) {
            throw new RentalServicesException(e.getMessage(), e);
        }
    }

    public List<Cliente> consultarClientes() throws RentalServicesException {
        try{
            return serviciosAlquiler.consultarClientes();
        } catch (RentalServicesException e) {
            throw new RentalServicesException(e.getMessage(), e);
        }
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

    private void clear() {
        setNombre("");
        setDocumento(0);
        setEmail("");
        setDireccion("");
        setTelefono("");
    }
}
