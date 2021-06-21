package net.ent.etrs.librairie.model.facades;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.collections4.IterableUtils;

import net.ent.etrs.librairie.model.daos.PublicationDao;
import net.ent.etrs.librairie.model.entities.Publication;

@Stateless
public class FacadePublication {
	@Inject
	private PublicationDao daoPublication;
	
	public List<Publication> listerPublications() {
		return IterableUtils.toList(this.daoPublication.findAll());
	}

	public Optional<Publication> getPublicationById(Long id) {
		return Optional.ofNullable(this.daoPublication.findOne(id));
	}
	
	public Publication sauvegarder(Publication publication) {
		return this.daoPublication.save(publication);
	}
	
	public void supprimer(Long id) {
		this.daoPublication.delete(id);
	}
	
	public Map<String, Long> statistiquesPublicationsPerDate() {
		return this.daoPublication.extractPublicationPerDate();
		
	}
	
	public void supprimer(Publication publication) throws Exception {
		try {
			this.daoPublication.delete(publication);
		} catch (Exception e) {
			throw new Exception(e);
		}
		
	}
}
