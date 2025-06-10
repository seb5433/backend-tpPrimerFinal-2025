package py.com.progweb.examenfinal.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ServicioDTO {
    private Integer id;
    private Date fecha;
    private String descripcionGeneral;
    private Integer kilometrajeActual;
    private BigDecimal costoTotal;
    private List<DetalleServicioDTO> detalles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
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

    public List<DetalleServicioDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleServicioDTO> detalles) {
        this.detalles = detalles;
    }
}