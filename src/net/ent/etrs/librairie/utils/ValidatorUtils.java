package net.ent.etrs.librairie.utils;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;

public final class ValidatorUtils {

	/**
	 * Validator.
	 */
	private static final Validator VALIDATOR;

	static
	{
		VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();
	}

	/**
	 * Constructeur.
	 */
	private ValidatorUtils()
	{
	}


	/**
	 * Valide les contraintes de l'objet.
	 *
	 * @param objet
	 *            Objet a valider
	 * @return retourne la liste des violation de contraintes.
	 */
	public static void validate(final Object objet) throws ValidationException
	{
		Set<ConstraintViolation<Object>> contraintesViolees =  VALIDATOR.validate(objet);
		if (!contraintesViolees.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for (ConstraintViolation<Object> c : contraintesViolees) {
				sb.append(String.format("%s : %s%n",c.getPropertyPath(),c.getMessage()));
			}
			throw new ValidationException(sb.toString());
		}		
		
	}
}
