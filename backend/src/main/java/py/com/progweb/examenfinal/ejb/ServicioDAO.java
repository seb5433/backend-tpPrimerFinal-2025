package py.com.progweb.examenfinal.ejb;

import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import py.com.progweb.examenfinal.model.Servicio;

@Local
public interface ServicioDAO {
    void crear(Servicio servicio);
    Servicio obtenerPorId(Integer id);
    List<Servicio> listarTodos();
    List<Servicio> listarPorClienteYFecha(Integer idCliente, Date fecha);
    void actualizar(Servicio servicio);
    void eliminar(Integer id);
}