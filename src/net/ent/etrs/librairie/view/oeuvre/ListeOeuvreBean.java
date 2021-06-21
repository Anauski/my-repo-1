package net.ent.etrs.librairie.view.oeuvre;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import net.ent.etrs.librairie.model.entities.Oeuvre;
import net.ent.etrs.librairie.model.facades.FacadeOeuvre;
import net.ent.etrs.librairie.utils.JsfUtils;

@SuppressWarnings("serial")
@Named
@ViewScoped
public class ListeOeuvreBean implements Serializable {

	@Inject
	private FacadeOeuvre facadeOeuvre;
	
	@Getter @Setter
	private List<Oeuvre> oeuvres;
	
	@PostConstruct
	public void init() {
		this.oeuvres = this.facadeOeuvre.listerOeuvres();
	}
	
	public void delete(Oeuvre oeuvre) {
		try {
			this.facadeOeuvre.supprimer(oeuvre);
		} catch (Exception e) {
			JsfUtils.sendGrowlMessage(FacesMessage.SEVERITY_ERROR, "error lors de la supression de ", oeuvre);
		}
		
		this.init();
	}
	
	public void modifier(Oeuvre oeuvre) {
		try {
			JsfUtils.putInFlashScope("oeuvre", oeuvre);
		} catch (Exception e) {
			JsfUtils.sendGrowlMessage(FacesMessage.SEVERITY_ERROR, "erreur lors de la modification", oeuvre);
		}
		
		JsfUtils.sendGrowlMessage(FacesMessage.SEVERITY_INFO, "Vous allez modifié l'auteur "+oeuvre.getNom(), oeuvre);
	}
	
}
