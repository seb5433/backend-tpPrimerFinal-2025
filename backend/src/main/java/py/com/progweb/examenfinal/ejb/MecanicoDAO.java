package py.com.progweb.examenfinal.ejb;

import java.util.List;
import javax.ejb.Local;
import py.com.progweb.examenfinal.model.Mecanico;

@Local
public interface MecanicoDAO {
    void crear(Mecanico mecanico);
    Mecanico obtenerPorId(Integer id);
    List<Mecanico> listarTodos();
    void actualizar(Mecanico mecanico);
    void eliminar(Integer id);
}