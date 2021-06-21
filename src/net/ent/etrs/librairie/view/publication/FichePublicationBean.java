package net.ent.etrs.librairie.view.publication;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.file.UploadedFile;

import lombok.Getter;
import lombok.Setter;
import net.ent.etrs.librairie.model.entities.MaisonEdition;
import net.ent.etrs.librairie.model.entities.Oeuvre;
import net.ent.etrs.librairie.model.entities.Publication;
import net.ent.etrs.librairie.model.entities.references.Pays;
import net.ent.etrs.librairie.model.entities.references.TypePublication;
import net.ent.etrs.librairie.model.facades.FacadeMaisonEdition;
import net.ent.etrs.librairie.model.facades.FacadeOeuvre;
import net.ent.etrs.librairie.model.facades.FacadePublication;
import net.ent.etrs.librairie.utils.JsfUtils;

@SuppressWarnings("serial")
@Named
@ViewScoped
public class FichePublicationBean implements Serializable {

	@Inject
	private FacadePublication facadePublication;

	@Inject
	private FacadeOeuvre facadeOeuvre;

	@Inject
	private FacadeMaisonEdition facadeMaisonEdition;

	@Inject
	@Getter
	@Setter
	private Publication publication;

	@Getter
	@Setter
	private UploadedFile image;

	@Getter
	private List<Pays> pays = Arrays.asList(Pays.values());

	@Getter
	private List<TypePublication> typePublications = Arrays.asList(TypePublication.values());

	@Getter
	private List<Oeuvre> oeuvres;

	@Getter
	private List<MaisonEdition> melist;

	@PostConstruct
	public void init() {
		this.oeuvres = this.facadeOeuvre.listerOeuvres();
		this.melist = this.facadeMaisonEdition.listerMaisonEditions();
		Publication p = (Publication) JsfUtils.getFromFlashScope("publication");
		if (p != null) {
			this.publication = p;
		}
	}

	public void enregistrer() {
		try {
			if (this.image != null && this.image.getFileName() != null) {
				this.publication.setImage(this.image.getContent());
			}
			this.facadePublication.sauvegarder(publication);
		} catch (Exception e) {
			JsfUtils.sendGrowlMessage(FacesMessage.SEVERITY_ERROR, "erreur lors de l'enregistrement", publication);
		}
		
		JsfUtils.sendGrowlMessage(FacesMessage.SEVERITY_INFO, "enregistrement reussit", image);
	}
}
