package net.ent.etrs.librairie.model.daos.impl;

import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.Tuple;

import net.ent.etrs.librairie.model.daos.JpaBaseDao;
import net.ent.etrs.librairie.model.daos.PublicationDao;
import net.ent.etrs.librairie.model.entities.Publication;

public class PublicationDaoImpl extends JpaBaseDao<Publication, Long> implements PublicationDao{
	
	@Override
	public Map<String, Long> extractPublicationPerDate() {
		String PUBLICATIONS_BY_DATES = "SELECT p.date_publication as key, count(p) as value FROM Publication p GROUP BY p.date_publication";
		return entityManager.createQuery(PUBLICATIONS_BY_DATES, Tuple.class).getResultStream().collect(Collectors.toMap(
				tuple -> ((String) tuple.get("key")).toString(), 
				tuple -> ((Long) tuple.get("value")).longValue()));
	}
}
