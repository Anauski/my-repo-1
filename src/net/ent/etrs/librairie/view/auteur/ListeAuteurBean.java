package net.ent.etrs.librairie.view.auteur;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import lombok.Getter;
import lombok.Setter;
import net.ent.etrs.librairie.model.entities.Auteur;
import net.ent.etrs.librairie.model.facades.FacadeAuteur;
import net.ent.etrs.librairie.utils.JsfUtils;

@SuppressWarnings("serial")
@Named
@ViewScoped
public class ListeAuteurBean implements Serializable {

	@Inject
	private FacadeAuteur facadeAuteur;
	
	@Getter @Setter @Inject
	private LazyDataModelAuteur auteurs;
	
	@PostConstruct
	public void init() {

	}
	
	public void delete(Auteur auteur) {
		try {
			this.facadeAuteur.supprimer(auteur);
		} catch (Exception e) {
			JsfUtils.sendGrowlMessage(FacesMessage.SEVERITY_ERROR, "error lors de la supression de ", auteur);
		}
		this.init();
	}
	
	public void modifier(Auteur auteur) {
		try {
			JsfUtils.putInFlashScope("auteur", auteur);
		} catch (Exception e) {
			JsfUtils.sendGrowlMessage(FacesMessage.SEVERITY_ERROR, "erreur lors de la modification", auteur);
		}
		
		JsfUtils.sendGrowlMessage(FacesMessage.SEVERITY_INFO, "Vous allez modifié l'auteur "+auteur.getNom(), auteur);
	}
	
}
