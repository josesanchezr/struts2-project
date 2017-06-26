package com.empresa.struts.actions;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.empresa.struts.dao.DaoFactory;
import com.empresa.struts.dao.IDaoService;
import com.empresa.struts.models.Request;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Get a list of request that have information of institution or branch
 * 
 * @author joseluis
 *
 */
public class ListRequestAction extends ActionSupport {
	private static final long serialVersionUID = 1L;

	/**
	 * Store a list of request with all its information
	 */
	private List<Request> listRequest;

	/**
	 * It's used by the data access layer to get information of institutions
	 */
	private IDaoService iDaoService;

	/**
	 * Store a names of institutions' map
	 */
	private Map<String, String> mapInstitutions;

	/**
	 * Store the type of request, if it's equal to the LIST_REQUEST_NAME string,
	 * then when execute the listrequest action to get a institutions map, else
	 * to get a list of request with all information of institutions.
	 */
	private String typeRequest;

	/**
	 * Get list of request with all the information of institutions.
	 * 
	 * @return list of request
	 */
	public List<Request> getListRequest() {
		return listRequest;
	}

	/**
	 * Set a list of request
	 * 
	 * @param listRequest
	 *            list of request
	 */
	public void setListRequest(List<Request> listRequest) {
		this.listRequest = listRequest;
	}

	/**
	 * Get a map with names of institutions
	 * 
	 * @return map with names of institutions
	 */
	public Map<String, String> getMapInstitutions() {
		return mapInstitutions;
	}

	/**
	 * Set a map with names of institutions
	 * 
	 * @param mapInstitutions
	 *            map with names of institutions
	 */
	public void setMapInstitutions(Map<String, String> mapInstitutions) {
		this.mapInstitutions = mapInstitutions;
	}

	/**
	 * Get the type of request
	 * 
	 * @return type of request
	 */
	public String getTypeRequest() {
		return typeRequest;
	}

	/**
	 * Set the type of request
	 * 
	 * @param typeRequest
	 *            type of request
	 */
	public void setTypeRequest(String typeRequest) {
		this.typeRequest = typeRequest;
	}

	/**
	 * Execute the listrequest action
	 */
	public String execute() throws Exception {
		// Initialize the dao factory used to get the data access layer
		DaoFactory daoFactory = DaoFactory.getSingletonInstance();
		// Get the dao service to access to the information stored into a file
		iDaoService = daoFactory.getService(DaoFactory.FILE);
		// Validate if the type of request is equal to LIST_REQUEST_NAME
		// then obtain a map with names of institutions,
		// else obtain a list of request with all information of institutions
		if ("LIST_REQUEST_NAME".equals(typeRequest)) {
			List<Request> requests = iDaoService.getAllAsList();
			mapInstitutions = new LinkedHashMap<String, String>();
			for (Request request : requests) {
				mapInstitutions.put(request.getName(), request.getName());
			}
		} else {
			listRequest = iDaoService.getAllAsList();
		}
		return SUCCESS;
	}
}
