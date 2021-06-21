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

import net.ent.etrs.librairie.model.entities.Publication;
import net.ent.etrs.librairie.model.exception.ValidException;
import net.ent.etrs.librairie.model.facades.FacadePublication;
import net.ent.etrs.librairie.utils.ValidatorUtils;

@Path("/auteur")
public class FacadeRestPublication {
	@Inject
	private FacadePublication facade;
	
	
	@GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listerPublication() {
		return Response.ok(this.facade.listerPublications()).build();

	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAuteurbyId(@PathParam("id") Long id) {
		Optional<Publication> publicationO = this.facade.getPublicationById(id);
		if (publicationO.isPresent()) {
			return Response.ok(publicationO.get()).build();
		}else {
			return Response.status(Status.NOT_FOUND).build();
		}
		

	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deletebyId(@PathParam("id") Long id) throws Exception {
		if(this.facade.getPublicationById(id).isPresent()) {
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
	public Response create(Publication publication)  {
		try {
			ValidatorUtils.validate(publication);
			publication = this.facade.sauvegarder(publication);
			return Response.ok(publication).build();
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
	public Response patch(@PathParam("id") Long id ,Publication publication )  {
		publication = this.facade.sauvegarder(publication);
		return Response.ok(publication).build();
	}
}
