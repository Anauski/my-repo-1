package net.ent.etrs.librairie.model.facades;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.collections4.IterableUtils;

import net.ent.etrs.librairie.model.daos.OeuvreDao;
import net.ent.etrs.librairie.model.entities.Oeuvre;

@Stateless
public class FacadeOeuvre {
	
	@Inject
	private OeuvreDao daoOeuvre;
	
	public List<Oeuvre> listerOeuvres() {
		return IterableUtils.toList(this.daoOeuvre.findAll());
	}

	public Optional<Oeuvre> getOeuvreById(Long id) {
		return Optional.ofNullable(this.daoOeuvre.findOne(id));
	}
	
	public Oeuvre sauvegarder(Oeuvre oeuvre) {
		return this.daoOeuvre.save(oeuvre);
	}
	
	public void supprimer(Long id) {
		this.daoOeuvre.delete(id);
	}
	
	public void supprimer(Oeuvre oeuvre) throws Exception {
		try {
			this.daoOeuvre.delete(oeuvre);
		} catch (Exception e) {
			throw new Exception(e);
		}
		
	}

	public Map<String, Long> statistiquesOeuvresParAuteur() {
		return this.daoOeuvre.extractOeuvrePerAuteur();
		
	}
}
