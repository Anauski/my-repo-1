package net.ent.etrs.librairie.model.entities.references;


import lombok.Getter;


public enum Pays {
	
	FRANCE("France","français"),
	ANGLETERRE("Angleterre","anglais"),
	ALLEMAGNE("Allemagne","allemand"),
	ESPAGNE("Espagne","espagnol");
	
	@Getter
	private String libelle;

	@Getter
	private String langue;
	
	Pays(String string, String string2) {
		this.libelle=string;
		this.langue=string2;
	}
	
}
