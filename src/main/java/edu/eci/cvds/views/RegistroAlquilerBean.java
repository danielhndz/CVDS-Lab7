package edu.eci.cvds.views;

import com.google.inject.Inject;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.samples.entities.ItemRentado;
import edu.eci.cvds.samples.services.RentalServicesException;
import edu.eci.cvds.samples.services.ServiciosAlquiler;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
@ManagedBean(name = "registroAlquilerBean", eager = true)
@SessionScoped
public class RegistroAlquilerBean extends BasePageBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private ServiciosAlquiler serviciosAlquiler;

    private List<ItemRentado> itemsRentados;
    private long documento;
    private int itemId;
    private int numDays;

    public void registrarAlquiler() {
        try {
            Date date = new Date(System.currentTimeMillis());
            Item item = serviciosAlquiler.consultarItem(itemId);
            serviciosAlquiler.registrarAlquilerCliente(
                    date,
                    documento,
                    item,
                    numDays
            );
            clear();
        } catch (RentalServicesException e) {
            e.printStackTrace();
        }
    }

    private void clear() {
        setItemId(0);
        setItemsRentados(null);
        setNumDays(0);
    }

    public long calcularMulta(ItemRentado itemRentado) {
        try {
            LocalDate fechaFinRenta = itemRentado.getFechaFinRenta().toLocalDate();
            LocalDate fechaHoy = new Date(System.currentTimeMillis()).toLocalDate();

            if (fechaHoy.isAfter(fechaFinRenta)) {
                long days = ChronoUnit.DAYS.between(fechaFinRenta, fechaHoy);
                return days * itemRentado.getItem().getTarifaxDia();
            }

            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public long calcularCostoAlquiler() {
        try {
            if (itemId != 0 && numDays != 0) {
                return serviciosAlquiler.consultarCostoAlquiler(itemId, numDays);
            }
            return 0;
        } catch (RentalServicesException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private List<ItemRentado> fetchItemsRentados() {
        try {
            return serviciosAlquiler.consultarItemsCliente(documento);
        } catch (RentalServicesException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<ItemRentado> getItemsRentados() {
        itemsRentados = fetchItemsRentados();
        return itemsRentados;
    }

    public void setItemsRentados(List<ItemRentado> itemsRentados) {
        this.itemsRentados = itemsRentados;
    }

    public long getDocumento() {
        return documento;
    }

    public void setDocumento(long documento) {
        this.documento = documento;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getNumDays() {
        return numDays;
    }

    public void setNumDays(int numDays) {
        this.numDays = numDays;
    }
}
