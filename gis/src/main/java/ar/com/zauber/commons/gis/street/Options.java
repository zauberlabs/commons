package ar.com.zauber.commons.gis.street;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Options {
	IGNORE_COMMON_WORDS() {
		/**
		 * Esta implementación filtra remueve palabras comunes del string de búsqueda.
		 */
		@Override
		public String filter(String street) {
			final String[] palabrasAFiltrar = {"avenida"};
			
			for (String palabra : palabrasAFiltrar) {
				Pattern pattern = Pattern.compile("(^|\\G|\\s)("+palabra+")($|\\s)", Pattern.CASE_INSENSITIVE);
				Matcher matcher = pattern.matcher(street);
				street = matcher.replaceAll("$1");
			}
			return street;
		}
		
	},
	REMOVE_EXTRA_SPACES() {
		@Override
		public String filter(String street) {
			return street.replaceAll("\\s+", " ").trim();
		}
	};
	
	/**
	 * Filtra caracteres en el {@link String} recibido como parámetro.
	 * La implementación default retorna el mismo {@link String} que recibe.
	 * @param street el nombre de la calle a filtrar.
	 * @return el nombre de la calle filtrado
	 */
	public String filter(final String street) {
		return street;
	}
}
