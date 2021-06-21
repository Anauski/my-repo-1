package net.ent.etrs.librairie.model.facades;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.collections4.IterableUtils;

import net.ent.etrs.librairie.model.daos.MaisonEditionDao;
import net.ent.etrs.librairie.model.entities.MaisonEdition;

@Stateless
public class FacadeMaisonEdition {
	@Inject
	private MaisonEditionDao daoMaisonEdtion;
	
	public List<MaisonEdition> listerMaisonEditions() {
		return IterableUtils.toList(this.daoMaisonEdtion.findAll());
	}

	public Optional<MaisonEdition> getMaisonEditionById(Long id) {
		return Optional.ofNullable(this.daoMaisonEdtion.findOne(id));
	}
	
	public MaisonEdition sauvegarder(MaisonEdition oeuvre) {
		return this.daoMaisonEdtion.save(oeuvre);
	}
	
	public void supprimer(MaisonEdition oeuvre) {
		this.daoMaisonEdtion.delete(oeuvre);
	}

	public void supprimer1(Long id) {
		this.daoMaisonEdtion.delete(id);
		
	}
}
