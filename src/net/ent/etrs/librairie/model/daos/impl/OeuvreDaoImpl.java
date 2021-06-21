package net.ent.etrs.librairie.model.daos.impl;

import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.Tuple;

import net.ent.etrs.librairie.model.daos.JpaBaseDao;
import net.ent.etrs.librairie.model.daos.OeuvreDao;
import net.ent.etrs.librairie.model.entities.Oeuvre;


public class OeuvreDaoImpl extends JpaBaseDao<Oeuvre, Long> implements OeuvreDao{

	@Override
	public Map<String, Long> extractOeuvrePerAuteur() {
		String OEUVRE_BY_AUTEURS = "SELECT o.auteur.nom as key, count(o) as value FROM Oeuvre o GROUP BY o.auteur.nom";
		return entityManager.createQuery(OEUVRE_BY_AUTEURS, Tuple.class).getResultStream().collect(Collectors.toMap(
				tuple -> ((String) tuple.get("key")).toString(), 
				tuple -> ((Long) tuple.get("value")).longValue()));
	}
	
}
