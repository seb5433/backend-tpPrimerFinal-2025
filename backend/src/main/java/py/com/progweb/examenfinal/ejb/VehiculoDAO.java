package py.com.progweb.examenfinal.ejb;

import java.util.List;
import javax.ejb.Local;
import py.com.progweb.examenfinal.model.Vehiculo;

@Local
public interface VehiculoDAO {
    void crear(Vehiculo vehiculo);
    Vehiculo obtenerPorId(Integer id);
    List<Vehiculo> listarTodos();
    List<Vehiculo> listarPorCliente(Integer idCliente);
    void actualizar(Vehiculo vehiculo);
    void eliminar(Integer id);
}