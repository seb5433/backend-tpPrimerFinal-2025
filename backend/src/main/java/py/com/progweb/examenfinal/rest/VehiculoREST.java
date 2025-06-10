package py.com.progweb.examenfinal.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import py.com.progweb.examenfinal.ejb.ClienteDAO;
import py.com.progweb.examenfinal.ejb.VehiculoDAO;
import py.com.progweb.examenfinal.input.VehiculoInput;
import py.com.progweb.examenfinal.model.Cliente;
import py.com.progweb.examenfinal.model.TipoVehiculo;
import py.com.progweb.examenfinal.model.Vehiculo;

@Path("/vehiculos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class VehiculoREST {

    @EJB
    private VehiculoDAO vehiculoDAO;

    @EJB
    private ClienteDAO clienteDAO;

    @GET
    public Response listar(@QueryParam("clienteId") Integer clienteId) {
        List<Vehiculo> vehiculos = (clienteId != null)
                ? vehiculoDAO.listarPorCliente(clienteId)
                : vehiculoDAO.listarTodos();
        return Response.ok(vehiculos).build();
    }

    @GET
    @Path("/{id}")
    public Response obtenerPorId(@PathParam("id") Integer id) {
        Vehiculo vehiculo = vehiculoDAO.obtenerPorId(id);
        if (vehiculo == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Vehículo no encontrado con id: " + id)
                    .build();
        }
        return Response.ok(vehiculo).build();
    }

    @POST
    public Response crear(VehiculoInput input) {
        try {
            Cliente cliente = clienteDAO.obtenerPorId(input.getIdCliente());
            if (cliente == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Cliente no encontrado con id: " + input.getIdCliente())
                        .build();
            }
            Vehiculo vehiculo = new Vehiculo();
            vehiculo.setMarca(input.getMarca());
            vehiculo.setNumeroChapa(input.getNumeroChapa());
            vehiculo.setModelo(input.getModelo());
            vehiculo.setAnio(input.getAnio());
            vehiculo.setTipo(TipoVehiculo.valueOf(input.getTipo()));
            vehiculo.setCliente(cliente);
            vehiculoDAO.crear(vehiculo);
            return Response.status(Response.Status.CREATED).entity(vehiculo).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error al crear vehículo: " + e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response actualizar(@PathParam("id") Integer id, VehiculoInput input) {
        try {
            Vehiculo vehiculo = vehiculoDAO.obtenerPorId(id);
            if (vehiculo == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Vehículo no encontrado con id: " + id)
                        .build();
            }
            Cliente cliente = clienteDAO.obtenerPorId(input.getIdCliente());
            if (cliente == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Cliente no encontrado con id: " + input.getIdCliente())
                        .build();
            }
            vehiculo.setMarca(input.getMarca());
            vehiculo.setNumeroChapa(input.getNumeroChapa());
            vehiculo.setModelo(input.getModelo());
            vehiculo.setAnio(input.getAnio());
            vehiculo.setTipo(TipoVehiculo.valueOf(input.getTipo()));
            vehiculo.setCliente(cliente);
            vehiculoDAO.actualizar(vehiculo);
            return Response.ok(vehiculo).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error al actualizar vehículo: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") Integer id) {
        try {
            vehiculoDAO.eliminar(id);
            return Response.ok("Vehículo eliminado exitosamente").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error al eliminar vehículo: " + e.getMessage())
                    .build();
        }
    }
}