package net.ent.etrs.librairie.view.stats;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import com.github.javafaker.Faker;

import net.ent.etrs.librairie.model.entities.Auteur;
import net.ent.etrs.librairie.model.entities.MaisonEdition;
import net.ent.etrs.librairie.model.entities.Oeuvre;
import net.ent.etrs.librairie.model.entities.Publication;
import net.ent.etrs.librairie.model.entities.references.Pays;
import net.ent.etrs.librairie.model.entities.references.TypePublication;
import net.ent.etrs.librairie.model.facades.FacadeAuteur;
import net.ent.etrs.librairie.model.facades.FacadeMaisonEdition;
import net.ent.etrs.librairie.model.facades.FacadeOeuvre;
import net.ent.etrs.librairie.model.facades.FacadePublication;

@Startup
@Singleton
public class initBean {
	@Inject
	private FacadeOeuvre facadeOeuvre;
	@Inject
	private FacadePublication facadePublication;
	@Inject
	private FacadeAuteur facadeAuteur;
	@Inject
	private FacadeMaisonEdition facadeMaisonEdition;

	@PostConstruct
	public void init() {
		Faker faker = new Faker(new Locale("fr"));
		List<Auteur> lsAuteurs = new ArrayList<>();
		List<MaisonEdition> lsMaisonEditions = new ArrayList<MaisonEdition>();
		List<Oeuvre> lsOeuvres = new ArrayList<Oeuvre>();
		List<Publication> lsPublications = new ArrayList<Publication>();
		// init auteur
		if (this.facadeAuteur.listerAuteurs().size() == 0 || this.facadeAuteur.listerAuteurs() == null) {

			for (int i = 0; i < 3000; i++) {
				lsAuteurs.add(i, new Auteur("n", "n", LocalDate.of(2000, 11, 11), Pays.ESPAGNE));
				lsAuteurs.get(i).setNom(faker.name().lastName());
				lsAuteurs.get(i).setDateNaissance(convertToLocalDateViaMilisecond(faker.date().birthday()));
				lsAuteurs.get(i).setPaysNaissance(recupRandomPays());
				lsAuteurs.get(i).setPrenom(faker.name().firstName());
			}

			for (Auteur a : lsAuteurs) {
				this.facadeAuteur.sauvegarder(a);
			}
		}
		//init oeuvre
		if (this.facadeOeuvre.listerOeuvres().size() == 0 || this.facadeOeuvre.listerOeuvres() == null) {
			for (int i = 0; i < 10; i++) {
				lsOeuvres.add(i, new Oeuvre("n",LocalDate.of(2000, 11,11),  new Auteur("n", "n", LocalDate.of(2000, 11, 11), Pays.ESPAGNE)));
				lsOeuvres.get(i).setNom(faker.book().title());
				lsOeuvres.get(i).setAuteur(recupererRandomAuteur());
				lsOeuvres.get(i)
						.setDateEcriture(convertToLocalDateViaMilisecond(faker.date().between(
								convertToDateViaInstant(lsOeuvres.get(i).getAuteur().getDateNaissance()),
								convertToDateViaInstant(LocalDate.now()))));
			}

			for (Oeuvre o : lsOeuvres) {
				this.facadeOeuvre.sauvegarder(o);
			}
		}
		//init maison edition
		if (this.facadeMaisonEdition.listerMaisonEditions().size() == 0
				|| this.facadeMaisonEdition.listerMaisonEditions() == null) {
			for (int i = 0; i < 4; i++) {
				lsMaisonEditions.add(new MaisonEdition());
				lsMaisonEditions.get(i).setNom(faker.company().name());
				lsMaisonEditions.get(i).setPays(recupRandomPays());
			}

			for (MaisonEdition m : lsMaisonEditions) {
				this.facadeMaisonEdition.sauvegarder(m);
			}
		}

		//init publication
		if (this.facadePublication.listerPublications().size() == 0
				|| this.facadePublication.listerPublications() == null) {
			for (int i = 0; i < this.facadeOeuvre.listerOeuvres().size(); i++) {
				
				Oeuvre o = new Oeuvre("n",LocalDate.of(2000, 11,11),  new Auteur("n", "n", LocalDate.of(2000, 11, 11), Pays.ESPAGNE));
				MaisonEdition m =  new MaisonEdition("n", Pays.ANGLETERRE);
				
				
				lsPublications.add(new Publication(LocalDate.of(2000, 12,1),BigDecimal.TEN, 10,Pays.ALLEMAGNE, o, TypePublication.BD, m ));
				lsPublications.get(i).setOeuvre(this.facadeOeuvre.listerOeuvres().get(i));
				lsPublications.get(i)
						.setDatePublication(convertToLocalDateViaMilisecond(faker.date().between(
								convertToDateViaInstant(lsPublications.get(i).getDatePublication()),
								convertToDateViaInstant(LocalDate.now()))));
				
				lsPublications.get(i).setLangue(recupRandomPays());
				lsPublications.get(i).setNbPages(randomNb(10, 1000));
				lsPublications.get(i).setTypePublication(recupTypePublication());
				lsPublications.get(i).setMaisonEdition(recupRandomMaisonEdition());
				lsPublications.get(i).setPrix(generateRandomBigDecimalFromRange(10, 200));
			}
			for (Publication publication : lsPublications) {
				this.facadePublication.sauvegarder(publication);
			}
		}

	}

	
	
	
	
	//Methode de recuperation random==============================================================================
	public BigDecimal generateRandomBigDecimalFromRange(float min, float max) {
		return new BigDecimal(Math.random() * (max - min) + min);
	   
	}
	
	
	private Auteur recupererRandomAuteur() {
		Random random = new Random();
		int num = random.nextInt(this.facadeAuteur.listerAuteurs().size());
		return this.facadeAuteur.listerAuteurs().get(num);
	}

	public LocalDate convertToLocalDateViaMilisecond(Date dateToConvert) {
		return Instant.ofEpochMilli(dateToConvert.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public Date convertToDateViaInstant(LocalDate dateToConvert) {
		return java.util.Date.from(dateToConvert.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	private Pays recupRandomPays() {
		Random random = new Random();
		Pays[] tab = Pays.values();
		int num = random.nextInt(tab.length);
		return tab[num];

	}
	
	private MaisonEdition recupRandomMaisonEdition() {
		Random random = new Random();
		int num = random.nextInt(this.facadeMaisonEdition.listerMaisonEditions().size());
		return this.facadeMaisonEdition.listerMaisonEditions().get(num);
	}
	
	private TypePublication recupTypePublication() {
		Random random = new Random();
		TypePublication[] tab= TypePublication.values();
		int num = random.nextInt(tab.length);
		return tab[num];
	}
	private int randomNb(int min , int max) {
		Random random = new Random();
		return random.nextInt((max - min)+1) + min;
	}

}
