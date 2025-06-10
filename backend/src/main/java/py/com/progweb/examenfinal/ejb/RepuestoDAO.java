package py.com.progweb.examenfinal.ejb;

import java.util.List;
import javax.ejb.Local;
import py.com.progweb.examenfinal.model.Repuesto;

@Local
public interface RepuestoDAO {
    void crear(Repuesto repuesto);
    Repuesto obtenerPorId(Integer id);
    List<Repuesto> listarTodos();
    void actualizar(Repuesto repuesto);
    void eliminar(Integer id);
}