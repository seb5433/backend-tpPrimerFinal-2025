package py.com.progweb.examenfinal.dto;

import java.math.BigDecimal;
import java.util.List;

public class DetalleServicioDTO {
    private Integer id;
    private String descripcion;
    private BigDecimal costo;
    private List<RepuestoDTO> repuestos;
    private List<MecanicoDTO> mecanicos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public List<RepuestoDTO> getRepuestos() {
        return repuestos;
    }

    public void setRepuestos(List<RepuestoDTO> repuestos) {
        this.repuestos = repuestos;
    }

    public List<MecanicoDTO> getMecanicos() {
        return mecanicos;
    }

    public void setMecanicos(List<MecanicoDTO> mecanicos) {
        this.mecanicos = mecanicos;
    }
}