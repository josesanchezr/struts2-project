package com.empresa.struts.dao;

import java.util.List;
import java.util.Map;

import com.empresa.struts.models.Request;

/**
 * Interface used from the action class to access to the information, this
 * interface could have various implementations, in this moment only the
 * DaoRequestImpl class implements this interface to store information into
 * local file
 * 
 * @author joseluis
 *
 */
public interface IDaoService {

	/**
	 * Get a map with all the institutions stored
	 * 
	 * @return a map with information of institutions
	 */
	public Map<String, Request> getAll();

	/**
	 * Get a list with all the institution stored
	 * 
	 * @return a list with information of institutions
	 */
	public List<Request> getAllAsList();

	/**
	 * Get a list with information of institutions with the same name
	 * 
	 * @param name
	 *            institution name
	 * @return list of institutions
	 */
	public List<Request> getInstitutionsByName(String name);

	/**
	 * Get a institution headquarter
	 * 
	 * @param name
	 *            institution name
	 * @return institution headquarter
	 */
	public Request getHeadquarter(String name);

	/**
	 * Get the information of institution by its name
	 * 
	 * @param name
	 *            name of institution
	 * @return information of institution
	 */
	public Request getRequest(String name);

	/**
	 * Store a institution
	 * 
	 * @param request
	 *            information of institution
	 * @param name
	 *            name of institution
	 * @return success if stored the information of institution into file
	 */
	public String save(Request request, String name);

	/**
	 * Delete a institution
	 * 
	 * @param name
	 *            institution name
	 * @return success if deleted the institution, else return error
	 */
	public String delete(String name);

}
