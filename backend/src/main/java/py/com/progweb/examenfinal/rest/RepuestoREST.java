package py.com.progweb.examenfinal.rest;

import py.com.progweb.examenfinal.ejb.RepuestoDAO;
import py.com.progweb.examenfinal.model.Repuesto;
import py.com.progweb.examenfinal.input.RepuestoInput;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/repuestos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RepuestoREST {

    @EJB
    private RepuestoDAO repuestoDAO;

    @GET
    public Response listar() {
        List<Repuesto> repuestos = repuestoDAO.listarTodos();
        return Response.ok(repuestos).build();
    }

    @GET
    @Path("/{id}")
    public Response obtenerPorId(@PathParam("id") Integer id) {
        Repuesto repuesto = repuestoDAO.obtenerPorId(id);
        if (repuesto == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Repuesto no encontrado con id: " + id)
                    .build();
        }
        return Response.ok(repuesto).build();
    }

    @POST
    public Response crear(RepuestoInput input) {
        try {
            Repuesto repuesto = new Repuesto();
            repuesto.setNombre(input.getNombre());
            repuesto.setCodigo(input.getCodigo());
            repuestoDAO.crear(repuesto);
            return Response.status(Response.Status.CREATED).entity(repuesto).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error al crear repuesto: " + e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response actualizar(@PathParam("id") Integer id, RepuestoInput input) {
        try {
            Repuesto repuesto = repuestoDAO.obtenerPorId(id);
            if (repuesto == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Repuesto no encontrado con id: " + id)
                        .build();
            }
            repuesto.setNombre(input.getNombre());
            repuesto.setCodigo(input.getCodigo());
            repuestoDAO.actualizar(repuesto);
            return Response.ok(repuesto).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error al actualizar repuesto: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") Integer id) {
        try {
            repuestoDAO.eliminar(id);
            return Response.ok("Repuesto eliminado exitosamente").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error al eliminar repuesto: " + e.getMessage())
                    .build();
        }
    }
}