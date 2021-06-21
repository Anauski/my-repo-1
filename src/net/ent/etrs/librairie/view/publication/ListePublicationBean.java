package net.ent.etrs.librairie.view.publication;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import net.ent.etrs.librairie.model.entities.Publication;
import net.ent.etrs.librairie.model.facades.FacadePublication;
import net.ent.etrs.librairie.utils.JsfUtils;

@SuppressWarnings("serial")
@Named
@ViewScoped
public class ListePublicationBean implements Serializable{
	
	@Inject
	private FacadePublication facadePublication;
	
	@Getter @Setter
	private List<Publication> publications;
	
	@PostConstruct
	public void init() {
		this.publications = this.facadePublication.listerPublications();
	}
	
	public void delete(Publication p) {
		try {
			this.facadePublication.supprimer(p);
		} catch (Exception e) {
			JsfUtils.sendGrowlMessage(FacesMessage.SEVERITY_ERROR, "error lors de la supression de ", p);
		}
		this.init();
	}
	
	public void modifier(Publication publication) {
		try {
			JsfUtils.putInFlashScope("publication", publication);
		} catch (Exception e) {
			JsfUtils.sendGrowlMessage(FacesMessage.SEVERITY_ERROR, "erreur lors de la modification", publication);
		}
		JsfUtils.sendGrowlMessage(FacesMessage.SEVERITY_INFO, "Vous allez modifié la publication de l'oeuvre "+publication.getOeuvre().getNom(), publication);
	}
}
