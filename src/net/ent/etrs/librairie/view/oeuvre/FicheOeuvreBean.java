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
import net.ent.etrs.librairie.model.entities.Auteur;
import net.ent.etrs.librairie.model.entities.Oeuvre;
import net.ent.etrs.librairie.model.facades.FacadeAuteur;
import net.ent.etrs.librairie.model.facades.FacadeOeuvre;
import net.ent.etrs.librairie.utils.JsfUtils;

@SuppressWarnings("serial")
@Named
@ViewScoped
public class FicheOeuvreBean implements Serializable {

	@Inject
	private FacadeOeuvre facadeOeuvre;

	@Inject
	private FacadeAuteur facadeAuteur;

	@Inject
	@Getter
	@Setter
	private Oeuvre oeuvre;

	@Getter
	private List<Auteur> auteurs;

	@PostConstruct
	public void init() {
		this.auteurs = this.facadeAuteur.listerAuteurs();

		Oeuvre o = (Oeuvre) JsfUtils.getFromFlashScope("oeuvre");
		if (o != null) {
			this.oeuvre = o;
		}
	}

	public void enregistrer() {
		try {
			this.facadeOeuvre.sauvegarder(oeuvre);
		} catch (Exception e) {
			JsfUtils.sendGrowlMessage(FacesMessage.SEVERITY_ERROR, "erreur lors de l'enregistrement", oeuvre);
		}
		
		JsfUtils.sendGrowlMessage(FacesMessage.SEVERITY_INFO, "enregistrement reussit", oeuvre);
	}

}
