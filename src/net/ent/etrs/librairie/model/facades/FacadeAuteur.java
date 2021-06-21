package net.ent.etrs.librairie.model.facades;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.collections4.IterableUtils;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

import net.ent.etrs.librairie.model.daos.AuteurDao;
import net.ent.etrs.librairie.model.entities.Auteur;

@Stateless
public class FacadeAuteur {
	
	@Inject
	private AuteurDao daoAuteur;
	
	public List<Auteur> listerAuteurs() {
		return IterableUtils.toList(this.daoAuteur.findAll());
	}

	public Optional<Auteur> getAuteurById(Long id) {
		return Optional.ofNullable(this.daoAuteur.findOne(id));
	}
	
	public Auteur sauvegarder(Auteur auteur) {
		return this.daoAuteur.save(auteur);
	}
	
	public void supprimer(Auteur auteur) throws Exception {
		try {
			this.daoAuteur.delete(auteur);
		} catch (Exception e) {
			throw new Exception(e);
		}
		
	}
	public List<Auteur> list(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
		return this.daoAuteur.list(first, pageSize, sortBy, filterBy);
	}
	
	public long count(Map<String, FilterMeta> filterBy) {
		return this.daoAuteur.count( filterBy);
	}

	public void supprimer(Long id) throws Exception {
		try {
			this.daoAuteur.delete(id);
		} catch (Exception e) {
			throw new Exception(e);
		}
		
	}


}
