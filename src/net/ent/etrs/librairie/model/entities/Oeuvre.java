package net.ent.etrs.librairie.model.entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.ent.etrs.librairie.model.entities.references.C;

@SuppressWarnings("serial")
@NoArgsConstructor
@Entity
@Table(name = "Oeuvre")
public class Oeuvre implements Serializable {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter
	@Column(name="ID")
	private Long id;
	
	@Getter @Setter
	@Column(name="NOM", nullable = false)
	@NotBlank(message=C.NOM_NOT_BLANK)
	@Length(min=1,max=100,message="Le nom de l'oeuvre doit etre compris entre 1 et 100 caractères")
	private String nom;
	
	@Getter @Setter
	@Column(name="DATE_ECRITURE", nullable=false)
	@NotNull(message = "La date d'ecriture doit etre renseigné")
	private LocalDate dateEcriture;
	
	@Getter @Setter
	@ManyToOne
	@JoinColumn(name="AUTEUR",nullable = false)
	@NotNull(message="L'auteur doit etre renseigné")
	private Auteur auteur;

	public Oeuvre(String nom,LocalDate dateEcriture, Auteur auteur) {
		super();
		this.nom = nom;
		this.dateEcriture = dateEcriture;
		this.auteur = auteur;
	}


	
	
	
	
}
