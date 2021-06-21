package net.ent.etrs.librairie.model.facades.api;

import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import net.ent.etrs.librairie.model.entities.Oeuvre;
import net.ent.etrs.librairie.model.exception.ValidException;
import net.ent.etrs.librairie.model.facades.FacadeOeuvre;
import net.ent.etrs.librairie.utils.ValidatorUtils;

@Path("/oeuvre")
public class FacadeRestOeuvre {
	
	@Inject
	private FacadeOeuvre facade;

	@GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listerOeuvre() {
		return Response.ok(this.facade.listerOeuvres()).build();

	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAuteurbyId(@PathParam("id") Long id) {
		Optional<Oeuvre> oeuvreO = this.facade.getOeuvreById(id);
		if (oeuvreO.isPresent()) {
			return Response.ok(oeuvreO.get()).build();
		}else {
			return Response.status(Status.NOT_FOUND).build();
		}
		

	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deletebyId(@PathParam("id") Long id) throws Exception {
		if(this.facade.getOeuvreById(id).isPresent()) {
			try {
				this.facade.supprimer(id);
				return Response.ok().build();
			} catch (Exception e) {
				return Response.serverError().build();		
			}
			
		}else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	

	@POST
	@Path("")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(Oeuvre oeuvre)  {
		try {
			ValidatorUtils.validate(oeuvre);
			oeuvre = this.facade.sauvegarder(oeuvre);
			return Response.ok(oeuvre).build();
		} catch(ValidException e) {
			return Response.status(Status.NOT_ACCEPTABLE).entity(e.mapViolations).build();
		} catch (Exception e) {
			return Response.serverError().build();	
			
		}
		
		

	}
	

	@PATCH
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response patch(@PathParam("id") Long id ,Oeuvre oeuvre )  {
		oeuvre = this.facade.sauvegarder(oeuvre);
		return Response.ok(oeuvre).build();
	}

}
