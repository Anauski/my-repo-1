package net.ent.etrs.librairie.model.entities;

import java.io.Serializable;
import java.time.LocalDate;

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
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.ent.etrs.librairie.model.entities.references.C;
import net.ent.etrs.librairie.model.entities.references.Pays;

@SuppressWarnings("serial")
@NoArgsConstructor
@Entity
@Table(name = "AUTEUR")
public class Auteur implements Serializable {

	

	

	@Getter @Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Getter @Setter
	@NotBlank(message = C.NOM_NOT_BLANK)
	@Column(name="NOM",nullable = false)
	@Length(max = 40,min=3,message = C.NOM_TAILLE_MAX_MIN)
	private String nom;
	
	@Getter @Setter
	@NotBlank(message = C.PRENOM_NOT_BLANK)
	@Column(name="PRENOM", nullable=false)
	@Length(max=40,min=2,message = "Le prenom doit etre compris entre 2 et 40 caractères")
	private String prenom;
	
	@Getter @Setter
	@NotNull(message = C.DATE_NOT_NULL)
	@Past(message = "l'auteur doit etre né")
	@Column(name="DATE_NAISSANCE", nullable=false)
	private LocalDate dateNaissance;
	
	@Getter @Setter
	@NotNull(message = C.PAYS_NOT_NULL)
	@Enumerated(EnumType.STRING)
	@Column(name="PAYS")
	private Pays paysNaissance;

	public Auteur(String nom, String prenom,LocalDate dateNaissance,Pays paysNaissance) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.dateNaissance = dateNaissance;
		this.paysNaissance = paysNaissance;
	}
	
	
	
	
	
}
