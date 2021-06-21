package net.ent.etrs.librairie.model.daos;



import java.io.Serializable;
import java.util.Map;

import net.ent.etrs.librairie.model.entities.Oeuvre;

public interface OeuvreDao extends BaseDao<Oeuvre, Serializable>{

	Map<String, Long> extractOeuvrePerAuteur();

}
