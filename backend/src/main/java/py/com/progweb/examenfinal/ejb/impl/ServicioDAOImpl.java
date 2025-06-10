package py.com.progweb.examenfinal.ejb.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.com.progweb.examenfinal.ejb.ServicioDAO;
import py.com.progweb.examenfinal.model.Servicio;

@Stateless
public class ServicioDAOImpl implements ServicioDAO {

    @PersistenceContext(unitName = "EcommerceBD")
    private EntityManager em;

    @Override
    public void crear(Servicio servicio) {
        em.persist(servicio);
    }

    @Override
    public Servicio obtenerPorId(Integer id) {
        return em.find(Servicio.class, id);
    }

    @Override
    public List<Servicio> listarTodos() {
        Query q = em.createQuery("select s from Servicio s");
        return q.getResultList();
    }

    @Override
    public List<Servicio> listarPorClienteYFecha(Integer idCliente, Date fecha) {
        StringBuilder sb = new StringBuilder("select s from Servicio s");
        boolean joinVehiculo = idCliente != null;
        if (joinVehiculo) {
            sb.append(" join s.vehiculo v");
        }
        List<String> condiciones = new ArrayList<>();
        if (idCliente != null) {
            condiciones.add("v.cliente.idCliente = :idCliente");
        }
        if (fecha != null) {
            condiciones.add("s.fecha = :fecha");
        }
        if (!condiciones.isEmpty()) {
            sb.append(" where ");
            sb.append(String.join(" and ", condiciones));
        }
        Query q = em.createQuery(sb.toString());
        if (idCliente != null) {
            q.setParameter("idCliente", idCliente);
        }
        if (fecha != null) {
            q.setParameter("fecha", fecha);
        }
        return q.getResultList();
    }

    @Override
    public void actualizar(Servicio servicio) {
        em.merge(servicio);
    }

    @Override
    public void eliminar(Integer id) {
        Servicio servicio = obtenerPorId(id);
        if (servicio != null) {
            em.remove(servicio);
        }
    }
}