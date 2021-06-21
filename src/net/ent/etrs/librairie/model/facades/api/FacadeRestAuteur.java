package net.ent.etrs.librairie.model.facades.api;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

import net.ent.etrs.librairie.model.dto.DtoAuteur;
import net.ent.etrs.librairie.model.entities.Auteur;
import net.ent.etrs.librairie.model.entities.references.Pays;
import net.ent.etrs.librairie.model.exception.ValidException;
import net.ent.etrs.librairie.model.facades.FacadeAuteur;
import net.ent.etrs.librairie.utils.ValidatorUtils;

@Path("/auteur")
public class FacadeRestAuteur {

	@Inject
	private FacadeAuteur facade;

	@GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listerAuteur() {
		List <Auteur> listeAuteur = this.facade.listerAuteurs();
		List <DtoAuteur> listeDtoAuteur = new ArrayList<>();
		for (Auteur a : listeAuteur) {
			listeDtoAuteur.add(this.toDTO(a));
			
		}
		return Response.ok(listeDtoAuteur).build();

	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAuteurbyId(@PathParam("id") Long id) {
		Optional<Auteur> auteurO = this.facade.getAuteurById(id);
		if (auteurO.isPresent()) {
			return Response.ok(this.toDTO(auteurO.get())).build();
		}else {
			return Response.status(Status.NOT_FOUND).build();
		}
		

	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deletebyId(@PathParam("id") Long id) throws Exception {
		if(this.facade.getAuteurById(id).isPresent()) {
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
	public Response create(DtoAuteur dto)  {
		try {
			Auteur auteur = this.toEntity(dto);
			ValidatorUtils.validate(auteur);
			auteur = this.facade.sauvegarder(auteur);
			return Response.ok(auteur).build();
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
	public Response patch(@PathParam("id") Long id ,Auteur auteur )  {
		auteur = this.facade.sauvegarder(auteur);
		return Response.ok(auteur).build();
	}
	
	
	private DtoAuteur toDTO(Auteur auteur) {
		DtoAuteur dto = new DtoAuteur();
		dto.id = auteur.getId();
		dto.nom = auteur.getNom();
		dto.prenom = auteur.getPrenom();
		dto.dateNaissance = Date.from(auteur.getDateNaissance().atStartOfDay(ZoneId.systemDefault()).toInstant());
		dto.paysNaissance = auteur.getPaysNaissance().name();
		return dto;
	}
	
	private Auteur toEntity(DtoAuteur dto) {
		Auteur entity = new Auteur();
		entity.setId(dto.id);
		entity.setNom(dto.nom);
		entity.setPrenom(dto.prenom);
		entity.setDateNaissance(dto.dateNaissance.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		entity.setPaysNaissance(Pays.valueOf(dto.paysNaissance));
		return entity;
		
	}

}
