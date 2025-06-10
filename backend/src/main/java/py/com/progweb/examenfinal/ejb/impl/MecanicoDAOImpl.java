package py.com.progweb.examenfinal.ejb.impl;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.com.progweb.examenfinal.ejb.MecanicoDAO;
import py.com.progweb.examenfinal.model.Mecanico;

@Stateless
public class MecanicoDAOImpl implements MecanicoDAO {

    @PersistenceContext(unitName = "EcommerceBD")
    private EntityManager em;

    @Override
    public void crear(Mecanico mecanico) {
        em.persist(mecanico);
    }

    @Override
    public Mecanico obtenerPorId(Integer id) {
        return em.find(Mecanico.class, id);
    }

    @Override
    public List<Mecanico> listarTodos() {
        Query q = em.createQuery("select m from Mecanico m");
        return q.getResultList();
    }

    @Override
    public void actualizar(Mecanico mecanico) {
        em.merge(mecanico);
    }

    @Override
    public void eliminar(Integer id) {
        Mecanico mecanico = obtenerPorId(id);
        if (mecanico != null) {
            em.remove(mecanico);
        }
    }
}