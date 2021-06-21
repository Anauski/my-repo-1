package net.ent.etrs.librairie.model.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.ent.etrs.librairie.model.entities.references.Pays;
import net.ent.etrs.librairie.model.entities.references.TypePublication;

@SuppressWarnings("serial")
@NoArgsConstructor
@Entity
@Table(name = "PUBLICATION")
public class Publication implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	@Setter
	private Long id;

	@Getter
	@Setter
	@Column(nullable = false, name = "DATE_PUBLICATION")
	@NotNull(message = "la date de publication doit etre renseigné")
	private LocalDate datePublication;

	@Getter
	@Setter
	@Column(nullable = false, name = "PRIX")
	@NotNull(message = "le prix doit etre renseigné")
	private BigDecimal prix;

	@Getter
	@Setter
	@Column(nullable = false, name = "NOMBRE_PAGES")
	@Min(value = 10, message = "les pages doivent etre au minimum au nombre de 10")
	private int nbPages;

	@Getter
	@Setter
	@Column(name = "IMAGE", nullable = true)
	private byte[] image;

	@Getter
	@Setter
	@Enumerated(EnumType.STRING)
	@Column(name = "LANGUE", nullable = false)
	@NotNull(message = "la langue doit etre renseigné")
	private Pays langue;

	@Getter
	@Setter
	@ManyToOne
	@JoinColumn(name = "OEUVRE", nullable = false)
	@NotNull(message = "L'oeuvre doit etre renseigné")
	private Oeuvre oeuvre;

	@Getter
	@Setter
	@Column(name = "TYPE_PUBLICATION", nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull(message = "le type de publication doit etre renseigné")
	private TypePublication typePublication;

	@Getter
	@Setter
	@JoinColumn(name = "MAISON_EDITION", nullable = false)
	@ManyToOne
	@NotNull(message = "la maison d'edition doit etre renseigné")
	private MaisonEdition maisonEdition;

	public Publication(LocalDate datePublication, BigDecimal prix, int nbPages, Pays langue,
			Oeuvre oeuvre, TypePublication typePublication, MaisonEdition maisonEdition) {
		super();
		this.datePublication = datePublication;
		this.prix = prix;
		this.nbPages = nbPages;
		this.langue = langue;
		this.oeuvre = oeuvre;
		this.typePublication = typePublication;
		this.maisonEdition = maisonEdition;
	}

}
