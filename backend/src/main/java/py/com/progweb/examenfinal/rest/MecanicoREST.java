package py.com.progweb.examenfinal.rest;

import py.com.progweb.examenfinal.ejb.MecanicoDAO;
import py.com.progweb.examenfinal.input.MecanicoInput;
import py.com.progweb.examenfinal.model.Mecanico;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Path("/mecanicos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MecanicoREST {

    @EJB
    private MecanicoDAO mecanicoDAO;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @GET
    public Response listar() {
        List<Mecanico> mecanicos = mecanicoDAO.listarTodos();
        return Response.ok(mecanicos).build();
    }

    @GET
    @Path("/{id}")
    public Response obtenerPorId(@PathParam("id") Integer id) {
        Mecanico mecanico = mecanicoDAO.obtenerPorId(id);
        if (mecanico == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Mecánico no encontrado con id: " + id)
                    .build();
        }
        return Response.ok(mecanico).build();
    }

    @POST
    public Response crear(MecanicoInput input) {
        try {
            Date fechaIngreso = DATE_FORMAT.parse(input.getFechaIngreso());
            Mecanico mecanico = new Mecanico();
            mecanico.setNombre(input.getNombre());
            mecanico.setDireccion(input.getDireccion());
            mecanico.setTelefono(input.getTelefono());
            mecanico.setFechaIngreso(fechaIngreso);
            mecanico.setEspecialidad(input.getEspecialidad());
            mecanicoDAO.crear(mecanico);
            return Response.status(Response.Status.CREATED).entity(mecanico).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error al crear mecánico: " + e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response actualizar(@PathParam("id") Integer id, MecanicoInput input) {
        try {
            Mecanico mecanico = mecanicoDAO.obtenerPorId(id);
            if (mecanico == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Mecánico no encontrado con id: " + id)
                        .build();
            }
            Date fechaIngreso = DATE_FORMAT.parse(input.getFechaIngreso());
            mecanico.setNombre(input.getNombre());
            mecanico.setDireccion(input.getDireccion());
            mecanico.setTelefono(input.getTelefono());
            mecanico.setFechaIngreso(fechaIngreso);
            mecanico.setEspecialidad(input.getEspecialidad());
            mecanicoDAO.actualizar(mecanico);
            return Response.ok(mecanico).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error al actualizar mecánico: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") Integer id) {
        try {
            mecanicoDAO.eliminar(id);
            return Response.ok("Mecánico eliminado exitosamente").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error al eliminar mecánico: " + e.getMessage())
                    .build();
        }
    }
}