package py.com.progweb.examenfinal.rest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import py.com.progweb.examenfinal.dto.DetalleServicioDTO;
import py.com.progweb.examenfinal.dto.MecanicoDTO;
import py.com.progweb.examenfinal.dto.RepuestoDTO;
import py.com.progweb.examenfinal.dto.ServicioDTO;
import py.com.progweb.examenfinal.ejb.DetalleServicioDAO;
import py.com.progweb.examenfinal.ejb.MecanicoDAO;
import py.com.progweb.examenfinal.ejb.RepuestoDAO;
import py.com.progweb.examenfinal.ejb.ServicioDAO;
import py.com.progweb.examenfinal.ejb.VehiculoDAO;
import py.com.progweb.examenfinal.input.DetalleServicioInput;
import py.com.progweb.examenfinal.input.ServicioInput;
import py.com.progweb.examenfinal.model.DetalleServicio;
import py.com.progweb.examenfinal.model.Mecanico;
import py.com.progweb.examenfinal.model.Repuesto;
import py.com.progweb.examenfinal.model.Servicio;
import py.com.progweb.examenfinal.model.Vehiculo;

@Path("/servicios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ServicioREST {

    @EJB
    private ServicioDAO servicioDAO;

    @EJB
    private VehiculoDAO vehiculoDAO;

    @EJB
    private RepuestoDAO repuestoDAO;

    @EJB
    private MecanicoDAO mecanicoDAO;

    @EJB
    private DetalleServicioDAO detalleDAO;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @GET
    public Response listar(@QueryParam("clienteId") Integer clienteId,
                           @QueryParam("fecha") String fechaStr) {
        try {
            Date fecha = fechaStr != null ? DATE_FORMAT.parse(fechaStr) : null;
            List<Servicio> servicios = servicioDAO.listarPorClienteYFecha(clienteId, fecha);
            List<ServicioDTO> result = new ArrayList<>();
            for (Servicio servicio : servicios) {
                result.add(toDTO(servicio, true));
            }
            return Response.ok(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error al listar servicios: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response obtenerPorId(@PathParam("id") Integer id) {
        Servicio servicio = servicioDAO.obtenerPorId(id);
        if (servicio == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Servicio no encontrado con id: " + id)
                    .build();
        }
        return Response.ok(toDTO(servicio, true)).build();
    }

    @POST
    public Response crear(ServicioInput input) {
        try {
            Vehiculo vehiculo = vehiculoDAO.obtenerPorId(input.getIdVehiculo());
            if (vehiculo == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Vehículo no encontrado con id: " + input.getIdVehiculo())
                        .build();
            }
            Date fecha = DATE_FORMAT.parse(input.getFecha());
            Servicio servicio = new Servicio();
            servicio.setFecha(fecha);
            servicio.setDescripcionGeneral(input.getDescripcionGeneral());
            servicio.setKilometrajeActual(input.getKilometrajeActual());
            servicio.setCostoTotal(input.getCostoTotal());
            servicio.setVehiculo(vehiculo);
            servicioDAO.crear(servicio);
            if (input.getDetalles() != null) {
                for (DetalleServicioInput inDet : input.getDetalles()) {
                    DetalleServicio det = new DetalleServicio();
                    det.setServicio(servicio);
                    det.setDescripcion(inDet.getDescripcion());
                    det.setCosto(inDet.getCosto());
                    List<Repuesto> repuestos = new ArrayList<>();
                    if (inDet.getIdRepuestos() != null) {
                        for (Integer idRep : inDet.getIdRepuestos()) {
                            Repuesto rep = repuestoDAO.obtenerPorId(idRep);
                            if (rep != null) repuestos.add(rep);
                        }
                    }
                    det.setRepuestos(repuestos);
                    List<Mecanico> mecanicos = new ArrayList<>();
                    if (inDet.getIdMecanicos() != null) {
                        for (Integer idMec : inDet.getIdMecanicos()) {
                            Mecanico mec = mecanicoDAO.obtenerPorId(idMec);
                            if (mec != null) mecanicos.add(mec);
                        }
                    }
                    det.setMecanicos(mecanicos);
                    detalleDAO.crear(det);
                }
            }
            return Response.status(Response.Status.CREATED).entity(toDTO(servicio, true)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error al crear servicio: " + e.getMessage())
                    .build();
        }
    }

    private ServicioDTO toDTO(Servicio servicio, boolean includeDetalles) {
        ServicioDTO dto = new ServicioDTO();
        dto.setId(servicio.getIdServicio());
        dto.setFecha(servicio.getFecha());
        dto.setDescripcionGeneral(servicio.getDescripcionGeneral());
        dto.setKilometrajeActual(servicio.getKilometrajeActual());
        dto.setCostoTotal(servicio.getCostoTotal());
        if (includeDetalles && servicio.getDetalles() != null) {
            List<DetalleServicioDTO> detDtos = new ArrayList<>();
            for (DetalleServicio det : servicio.getDetalles()) {
                DetalleServicioDTO detDTO = new DetalleServicioDTO();
                detDTO.setId(det.getIdDetalleServicio());
                detDTO.setDescripcion(det.getDescripcion());
                detDTO.setCosto(det.getCosto());
                List<RepuestoDTO> repDtos = new ArrayList<>();
                for (Repuesto rep : det.getRepuestos()) {
                    repDtos.add(new RepuestoDTO(rep.getIdRepuesto(), rep.getNombre(), rep.getCodigo()));
                }
                detDTO.setRepuestos(repDtos);
                List<MecanicoDTO> mecDtos = new ArrayList<>();
                for (Mecanico mec : det.getMecanicos()) {
                    mecDtos.add(new MecanicoDTO(mec.getIdMecanico(), mec.getNombre(), mec.getDireccion(),
                            mec.getTelefono(), mec.getFechaIngreso(), mec.getEspecialidad()));
                }
                detDTO.setMecanicos(mecDtos);
                detDtos.add(detDTO);
            }
            dto.setDetalles(detDtos);
        }
        return dto;
    }
}