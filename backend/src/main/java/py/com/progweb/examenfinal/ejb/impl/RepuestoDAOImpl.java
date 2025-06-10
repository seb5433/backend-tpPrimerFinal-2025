package py.com.progweb.examenfinal.ejb.impl;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.com.progweb.examenfinal.ejb.RepuestoDAO;
import py.com.progweb.examenfinal.model.Repuesto;

@Stateless
public class RepuestoDAOImpl implements RepuestoDAO {

    @PersistenceContext(unitName = "EcommerceBD")
    private EntityManager em;

    @Override
    public void crear(Repuesto repuesto) {
        em.persist(repuesto);
    }

    @Override
    public Repuesto obtenerPorId(Integer id) {
        return em.find(Repuesto.class, id);
    }

    @Override
    public List<Repuesto> listarTodos() {
        Query q = em.createQuery("select r from Repuesto r");
        return q.getResultList();
    }

    @Override
    public void actualizar(Repuesto repuesto) {
        em.merge(repuesto);
    }

    @Override
    public void eliminar(Integer id) {
        Repuesto repuesto = obtenerPorId(id);
        if (repuesto != null) {
            em.remove(repuesto);
        }
    }
}