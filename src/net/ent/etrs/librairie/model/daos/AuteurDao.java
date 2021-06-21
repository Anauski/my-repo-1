package net.ent.etrs.librairie.model.daos;



import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

import net.ent.etrs.librairie.model.entities.Auteur;

public interface AuteurDao extends BaseDao<Auteur, Serializable>{
	public List<Auteur> list(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy);

	int count( Map<String, FilterMeta> filterBy);
}
