package py.com.progweb.examenfinal.ejb.impl;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.com.progweb.examenfinal.ejb.VehiculoDAO;
import py.com.progweb.examenfinal.model.Vehiculo;

@Stateless
public class VehiculoDAOImpl implements VehiculoDAO {

    @PersistenceContext(unitName = "EcommerceBD")
    private EntityManager em;

    @Override
    public void crear(Vehiculo vehiculo) {
        em.persist(vehiculo);
    }

    @Override
    public Vehiculo obtenerPorId(Integer id) {
        return em.find(Vehiculo.class, id);
    }

    @Override
    public List<Vehiculo> listarTodos() {
        Query q = em.createQuery("select v from Vehiculo v");
        return q.getResultList();
    }

    @Override
    public List<Vehiculo> listarPorCliente(Integer idCliente) {
        Query q = em.createQuery("select v from Vehiculo v where v.cliente.idCliente = :idCliente");
        q.setParameter("idCliente", idCliente);
        return q.getResultList();
    }

    @Override
    public void actualizar(Vehiculo vehiculo) {
        em.merge(vehiculo);
    }

    @Override
    public void eliminar(Integer id) {
        Vehiculo vehiculo = obtenerPorId(id);
        if (vehiculo != null) {
            em.remove(vehiculo);
        }
    }
}