package net.ent.etrs.librairie.view.maisonEdition;


import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import net.ent.etrs.librairie.model.entities.MaisonEdition;
import net.ent.etrs.librairie.model.facades.FacadeMaisonEdition;
import net.ent.etrs.librairie.utils.JsfUtils;

@SuppressWarnings("serial")
@Named
@ViewScoped
public class ListeMaisonEditionBean implements Serializable{
	
	@Inject
	private FacadeMaisonEdition facadeMaisonEdition;
	
	@Getter @Setter
	private List<MaisonEdition> maisonEditions;
	
	@PostConstruct
	public void init() {
		this.maisonEditions = this.facadeMaisonEdition.listerMaisonEditions();
	}
	
	public void delete(MaisonEdition maisonEdition) {
		try {
			this.facadeMaisonEdition.supprimer(maisonEdition);
		}catch(Exception e) {
			JsfUtils.sendGrowlMessage(FacesMessage.SEVERITY_ERROR, "error lors de la supression de ", maisonEdition);
		}
		this.init();
	}
	
	public void modifier(MaisonEdition maisonEdition) {
		try {
			JsfUtils.putInFlashScope("maisonEdition", maisonEdition);
		} catch (Exception e) {
			JsfUtils.sendGrowlMessage(FacesMessage.SEVERITY_ERROR, "erreur lors de la modification", maisonEdition);
		}
		
		JsfUtils.sendGrowlMessage(FacesMessage.SEVERITY_INFO, "Vous allez modifié l'auteur "+maisonEdition.getNom(), maisonEdition);
	}
	
}
