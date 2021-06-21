package net.ent.etrs.librairie.view.maisonEdition;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import net.ent.etrs.librairie.model.entities.MaisonEdition;
import net.ent.etrs.librairie.model.entities.references.Pays;
import net.ent.etrs.librairie.model.facades.FacadeMaisonEdition;
import net.ent.etrs.librairie.utils.JsfUtils;

@SuppressWarnings("serial")
@Named
@ViewScoped
public class FicheMaisonEditionBean implements Serializable {
	@Inject
	private FacadeMaisonEdition facadeMaisonEdition;
	
	@Inject
	@Getter @Setter
	private MaisonEdition maisonEdition;
	
	@Getter
	private List<Pays> pays = Arrays.asList(Pays.values());
	
	@PostConstruct
	public void init() {
		MaisonEdition me = (MaisonEdition) JsfUtils.getFromFlashScope("maisonEdition");
		if (me != null) {
			this.maisonEdition = me;
		}
	}
	
	public void enregistrer() {
		try {
			this.facadeMaisonEdition.sauvegarder(maisonEdition);
		} catch (Exception e) {
			JsfUtils.sendGrowlMessage(FacesMessage.SEVERITY_ERROR, "erreur lors de l'enregistrement", maisonEdition);
		}
		
		JsfUtils.sendGrowlMessage(FacesMessage.SEVERITY_INFO, "enregistrement reussit", maisonEdition);
	}
	

}

