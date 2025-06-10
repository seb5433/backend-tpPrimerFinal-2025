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
        // First fetch the service with detalles
        Query q1 = em.createQuery("select s from Servicio s left join fetch s.detalles where s.idServicio = :id");
        q1.setParameter("id", id);
        @SuppressWarnings("unchecked")
        List<Servicio> results = q1.getResultList();
        
        if (results.isEmpty()) {
            return null;
        }
        
        Servicio servicio = results.get(0);
        
        // If there are detalles, fetch repuestos and mecanicos separately
        if (servicio.getDetalles() != null && !servicio.getDetalles().isEmpty()) {
            // Fetch repuestos for all detalles
            Query q2 = em.createQuery("select d from DetalleServicio d left join fetch d.repuestos where d.servicio.idServicio = :id");
            q2.setParameter("id", id);
            q2.getResultList(); // This populates the repuestos
            
            // Fetch mecanicos for all detalles
            Query q3 = em.createQuery("select d from DetalleServicio d left join fetch d.mecanicos where d.servicio.idServicio = :id");
            q3.setParameter("id", id);
            q3.getResultList(); // This populates the mecanicos
        }
        
        return servicio;
    }

    @Override
    public List<Servicio> listarTodos() {
        Query q = em.createQuery("select s from Servicio s");
        @SuppressWarnings("unchecked")
        List<Servicio> result = q.getResultList();
        return result;
    }

    @Override
    public List<Servicio> listarPorClienteYFecha(Integer idCliente, Date fecha) {
        // First fetch servicios with detalles
        StringBuilder sb = new StringBuilder("select distinct s from Servicio s left join fetch s.detalles");
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
        @SuppressWarnings("unchecked")
        List<Servicio> servicios = q.getResultList();
        
        // If we have servicios with detalles, fetch repuestos and mecanicos separately
        if (!servicios.isEmpty()) {
            List<Integer> servicioIds = new ArrayList<>();
            for (Servicio s : servicios) {
                if (s.getDetalles() != null && !s.getDetalles().isEmpty()) {
                    servicioIds.add(s.getIdServicio());
                }
            }
            
            if (!servicioIds.isEmpty()) {
                // Fetch repuestos for all detalles
                Query q2 = em.createQuery("select d from DetalleServicio d left join fetch d.repuestos where d.servicio.idServicio in :ids");
                q2.setParameter("ids", servicioIds);
                q2.getResultList(); // This populates the repuestos
                
                // Fetch mecanicos for all detalles
                Query q3 = em.createQuery("select d from DetalleServicio d left join fetch d.mecanicos where d.servicio.idServicio in :ids");
                q3.setParameter("ids", servicioIds);
                q3.getResultList(); // This populates the mecanicos
            }
        }
        
        return servicios;
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