package net.ent.etrs.librairie.model.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.ent.etrs.librairie.model.entities.references.C;
import net.ent.etrs.librairie.model.entities.references.Pays;

@SuppressWarnings("serial")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "MAISON_EDITION")
public class MaisonEdition implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	@Getter @Setter
	private Long id;
	

	@Getter @Setter
	@NotBlank(message=C.NOM_NOT_BLANK)
	@Column(name="NOM", nullable=false)
	@Length(min=3,max=40,message = C.NOM_TAILLE_MAX_MIN)
	private String nom;

	@Getter @Setter
	@Enumerated(EnumType.STRING)
	@Column(name = "PAYS", nullable=false)
	@NotNull(message=C.PAYS_NOT_NULL)
	private Pays pays;

	public MaisonEdition(String nom, Pays pays) {
		super();
		this.nom = nom;
		this.pays = pays;
	}
	

	
}
