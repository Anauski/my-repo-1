package net.ent.etrs.librairie.view.auteur;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import net.ent.etrs.librairie.model.entities.Auteur;
import net.ent.etrs.librairie.model.facades.FacadeAuteur;

@SuppressWarnings("serial")
public class LazyDataModelAuteur extends LazyDataModel<Auteur>{
	@Inject
	private FacadeAuteur facadeAuteur;
 	
	@Override
	public List<Auteur> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
		List<Auteur> list = facadeAuteur.list(first, pageSize, sortBy, filterBy);
		this.setRowCount((int) this.facadeAuteur.count(filterBy));
		return list;
	}

}
