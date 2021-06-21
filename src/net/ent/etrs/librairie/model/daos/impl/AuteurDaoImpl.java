package net.ent.etrs.librairie.model.daos.impl;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.TypedQuery;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import net.ent.etrs.librairie.model.daos.AuteurDao;
import net.ent.etrs.librairie.model.daos.JpaBaseDao;
import net.ent.etrs.librairie.model.entities.Auteur;

public class AuteurDaoImpl extends JpaBaseDao<Auteur, Long> implements AuteurDao {


	@Override
	public List<Auteur> list(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
		
		String sql ="SELECT a FROM Auteur a  WHERE 1=1";
		String nom =null ;
	
		
		String prenom = null;
		
		if (filterBy.containsKey("nom")) {
			nom=(String) filterBy.get("nom").getFilterValue();
		}
		if (filterBy.containsKey("prenom")) {
			prenom=(String) filterBy.get("prenom").getFilterValue();
		}
		
		if(nom !=null) {
			sql += " and a.nom like :nom";
		}
		
		if(prenom !=null) {
			sql += " and a.prenom like :prenom";
		}
		if (!sortBy.isEmpty()) {
			sql += " ORDER BY ";
			for (Entry<String, SortMeta> e : sortBy.entrySet()) {
				sql += e.getValue().getField() + " " +(e.getValue().getOrder().equals(SortOrder.ASCENDING) ? "ASC" : "DESC") + ",";
				
			}
			sql = sql.substring(0, sql.length()-1);
		}
		
		System.out.println(sql);
		
		
		TypedQuery<Auteur> q = this.entityManager.createQuery(sql, Auteur.class);
		
		if(nom !=null) {
			q.setParameter("nom", nom+"%");
		}
		
		if(prenom !=null) {
			q.setParameter("prenom", prenom+"%");
		}
		q.setFirstResult(first);
		q.setMaxResults(pageSize);
		
		return q.getResultList();
	}
	@Override
	public int count(Map<String, FilterMeta> filterBy) {
		String sql ="SELECT count(a) FROM Auteur a  WHERE 1=1";
		String nom =null ;
	
		
		String prenom = null;
		
		if (filterBy.containsKey("nom")) {
			nom=(String) filterBy.get("nom").getFilterValue();
		}
		if (filterBy.containsKey("prenom")) {
			prenom=(String) filterBy.get("prenom").getFilterValue();
		}
		
		if(nom !=null) {
			sql += " and a.nom like :nom";
		}
		
		if(prenom !=null) {
			sql += " and a.prenom like :prenom";
		}
		
		TypedQuery<Long> q = this.entityManager.createQuery(sql, Long.class);
		
		if(nom !=null) {
			q.setParameter("nom", nom+"%");
		}
		
		if(prenom !=null) {
			q.setParameter("prenom", prenom+"%");
		}
		
		
		return q.getSingleResult().intValue();
		
	}

}
