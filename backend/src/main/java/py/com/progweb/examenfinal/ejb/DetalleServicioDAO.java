package py.com.progweb.examenfinal.ejb;

import java.util.List;
import javax.ejb.Local;
import py.com.progweb.examenfinal.model.DetalleServicio;

@Local
public interface DetalleServicioDAO {
    void crear(DetalleServicio detalle);
    DetalleServicio obtenerPorId(Integer id);
    List<DetalleServicio> listarPorServicio(Integer idServicio);
    void actualizar(DetalleServicio detalle);
    void eliminar(Integer id);
}