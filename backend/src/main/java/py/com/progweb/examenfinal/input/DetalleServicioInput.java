package py.com.progweb.examenfinal.input;

import java.math.BigDecimal;
import java.util.List;

public class DetalleServicioInput {
    private String descripcion;
    private BigDecimal costo;
    private List<Integer> idRepuestos;
    private List<Integer> idMecanicos;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }

    public List<Integer> getIdRepuestos() {
        return idRepuestos;
    }

    public void setIdRepuestos(List<Integer> idRepuestos) {
        this.idRepuestos = idRepuestos;
    }

    public List<Integer> getIdMecanicos() {
        return idMecanicos;
    }

    public void setIdMecanicos(List<Integer> idMecanicos) {
        this.idMecanicos = idMecanicos;
    }
}