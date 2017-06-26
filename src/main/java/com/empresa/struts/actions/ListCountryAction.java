package com.empresa.struts.actions;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Get a list of countries
 * 
 * @author joseluis
 *
 */
public class ListCountryAction extends ActionSupport {
	private static final long serialVersionUID = 1L;

	/**
	 * Store a map of countries, it use the ISO 3166 standard.
	 */
	private Map<String, String> mapCountries;

	/**
	 * Execute the listcountries action
	 */
	public String execute() throws Exception {
		// Get list of countries, passing the default location
		mapCountries = getListOfCountries(Locale.getDefault());
		return SUCCESS;
	}

	/**
	 * Get the list of countries by location
	 * 
	 * @param locale
	 *            location
	 * @return a list of countries
	 */
	private Map<String, String> getListOfCountries(Locale locale) {
		// Get an array of string with the list of countries
		String[] locales = Locale.getISOCountries();
		Map<String, String> countries = new HashMap<String, String>();
		for (String countryCode : locales) {
			Locale obj = new Locale("", countryCode);
			// Store the code and name of country into the countries map
			countries.put(obj.getCountry(), obj.getDisplayCountry(locale));
		}
		return countries;
	}

	/**
	 * Get the countries map
	 * 
	 * @return the countries map
	 */
	public Map<String, String> getMapCountries() {
		return mapCountries;
	}

	/**
	 * Set the countries map
	 * 
	 * @param mapCountries
	 *            countries map
	 */
	public void setMapCountries(Map<String, String> mapCountries) {
		this.mapCountries = mapCountries;
	}
}
