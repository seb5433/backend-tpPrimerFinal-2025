package py.com.progweb.examenfinal.ejb.impl;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.com.progweb.examenfinal.ejb.DetalleServicioDAO;
import py.com.progweb.examenfinal.model.DetalleServicio;

@Stateless
public class DetalleServicioDAOImpl implements DetalleServicioDAO {

    @PersistenceContext(unitName = "EcommerceBD")
    private EntityManager em;

    @Override
    public void crear(DetalleServicio detalle) {
        em.persist(detalle);
    }

    @Override
    public DetalleServicio obtenerPorId(Integer id) {
        return em.find(DetalleServicio.class, id);
    }

    @Override
    public List<DetalleServicio> listarPorServicio(Integer idServicio) {
        Query q = em.createQuery("select d from DetalleServicio d where d.servicio.idServicio = :idServicio");
        q.setParameter("idServicio", idServicio);
        return q.getResultList();
    }

    @Override
    public void actualizar(DetalleServicio detalle) {
        em.merge(detalle);
    }

    @Override
    public void eliminar(Integer id) {
        DetalleServicio detalle = obtenerPorId(id);
        if (detalle != null) {
            em.remove(detalle);
        }
    }
}