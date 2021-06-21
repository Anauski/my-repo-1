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

import net.ent.etrs.librairie.model.entities.MaisonEdition;
import net.ent.etrs.librairie.model.exception.ValidException;
import net.ent.etrs.librairie.model.facades.FacadeMaisonEdition;
import net.ent.etrs.librairie.utils.ValidatorUtils;

public class FacadeRestMaisonEdition {
	@Inject
	private FacadeMaisonEdition facade;

	@GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listerMaisonEdition() {
		return Response.ok(this.facade.listerMaisonEditions()).build();

	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAuteurbyId(@PathParam("id") Long id) {
		Optional<MaisonEdition> maisonEditionO = this.facade.getMaisonEditionById(id);
		if (maisonEditionO.isPresent()) {
			return Response.ok(maisonEditionO.get()).build();
		}else {
			return Response.status(Status.NOT_FOUND).build();
		}
		

	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deletebyId(@PathParam("id") Long id) throws Exception {
		if(this.facade.getMaisonEditionById(id).isPresent()) {
			try {
				this.facade.supprimer1(id);
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
	public Response create(MaisonEdition maisonEdition)  {
		try {
			ValidatorUtils.validate(maisonEdition);
			maisonEdition = this.facade.sauvegarder(maisonEdition);
			return Response.ok(maisonEdition).build();
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
	public Response patch(@PathParam("id") Long id ,MaisonEdition maisonEdition )  {
		maisonEdition = this.facade.sauvegarder(maisonEdition);
		return Response.ok(maisonEdition).build();
	}
}
