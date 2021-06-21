package net.ent.etrs.librairie.model.daos;



import java.io.Serializable;
import java.util.Map;

import net.ent.etrs.librairie.model.entities.Publication;

public interface PublicationDao extends BaseDao<Publication, Serializable>{

	Map<String, Long> extractPublicationPerDate();

}
