package py.com.progweb.examenfinal.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "detalle_servicio")
public class DetalleServicio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_servicio")
    private Integer idDetalleServicio;

    @ManyToOne
    @JoinColumn(name = "id_servicio", nullable = false)
    private Servicio servicio;

    @Column(name = "descripcion", nullable = false, length = 500)
    private String descripcion;

    @Column(name = "costo", nullable = false, precision = 10, scale = 2)
    private BigDecimal costo;

    @ManyToMany
    @JoinTable(
        name = "detalle_servicio_repuesto",
        joinColumns = @JoinColumn(name = "id_detalle_servicio"),
        inverseJoinColumns = @JoinColumn(name = "id_repuesto")
    )
    private List<Repuesto> repuestos;

    @ManyToMany
    @JoinTable(
        name = "detalle_servicio_mecanico",
        joinColumns = @JoinColumn(name = "id_detalle_servicio"),
        inverseJoinColumns = @JoinColumn(name = "id_mecanico")
    )
    private List<Mecanico> mecanicos;

    public Integer getIdDetalleServicio() {
        return idDetalleServicio;
    }

    public void setIdDetalleServicio(Integer idDetalleServicio) {
        this.idDetalleServicio = idDetalleServicio;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
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

    public List<Repuesto> getRepuestos() {
        return repuestos;
    }

    public void setRepuestos(List<Repuesto> repuestos) {
        this.repuestos = repuestos;
    }

    public List<Mecanico> getMecanicos() {
        return mecanicos;
    }

    public void setMecanicos(List<Mecanico> mecanicos) {
        this.mecanicos = mecanicos;
    }
}