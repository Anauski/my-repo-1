package net.ent.etrs.librairie.view.auteur;

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
import net.ent.etrs.librairie.model.entities.Auteur;
import net.ent.etrs.librairie.model.entities.references.Pays;
import net.ent.etrs.librairie.model.facades.FacadeAuteur;
import net.ent.etrs.librairie.utils.JsfUtils;

@SuppressWarnings("serial")
@Named
@ViewScoped
public class FicheAuteurBean implements Serializable {

	@Inject
	private FacadeAuteur facadeAuteur;
	

	@Inject
	@Getter @Setter
	private Auteur auteur;

	@Getter
	private List<Pays> pays = Arrays.asList(Pays.values());

	@PostConstruct
	public void init() {
		Auteur a = (Auteur) JsfUtils.getFromFlashScope("auteur");
		if (a != null) {
			this.auteur = a;
		}
	}

	public void enregistrer() {
		try {
			this.facadeAuteur.sauvegarder(auteur);
		} catch (Exception e) {
			JsfUtils.sendGrowlMessage(FacesMessage.SEVERITY_ERROR, "erreur lors de l'enregistrement", auteur);
		}
		
		JsfUtils.sendGrowlMessage(FacesMessage.SEVERITY_INFO, "enregistrement reussit", auteur);
	}

}
