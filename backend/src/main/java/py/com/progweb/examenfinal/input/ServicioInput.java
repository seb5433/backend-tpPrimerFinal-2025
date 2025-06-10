package py.com.progweb.examenfinal.input;

import java.math.BigDecimal;
import java.util.List;

public class ServicioInput {
    private String fecha;
    private String descripcionGeneral;
    private Integer kilometrajeActual;
    private BigDecimal costoTotal;
    private Integer idVehiculo;
    private List<DetalleServicioInput> detalles;

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDescripcionGeneral() {
        return descripcionGeneral;
    }

    public void setDescripcionGeneral(String descripcionGeneral) {
        this.descripcionGeneral = descripcionGeneral;
    }

    public Integer getKilometrajeActual() {
        return kilometrajeActual;
    }

    public void setKilometrajeActual(Integer kilometrajeActual) {
        this.kilometrajeActual = kilometrajeActual;
    }

    public BigDecimal getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(BigDecimal costoTotal) {
        this.costoTotal = costoTotal;
    }

    public Integer getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Integer idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public List<DetalleServicioInput> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleServicioInput> detalles) {
        this.detalles = detalles;
    }
}