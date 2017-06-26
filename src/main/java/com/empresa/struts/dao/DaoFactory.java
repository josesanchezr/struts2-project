package com.empresa.struts.dao;

import java.util.HashMap;
import java.util.Map;

import com.empresa.struts.dao.file.DaoRequestImpl;

/**
 * It's used to initialize dao factory
 * 
 * @author joseluis
 *
 */
public class DaoFactory {

	/**
	 * Map of IDaoService, have the implementations of IDaoService used by data
	 * access layer
	 */
	private Map<String, IDaoService> factoryService;

	/**
	 * Store the dao factory, it's used to initialize only one instance using
	 * the singleton pattern
	 */
	private static DaoFactory daoFactory;

	/**
	 * Store the FILE constant with the file value
	 */
	public static final String FILE = "file";

	/**
	 * Constructor of the DaoFactory class
	 */
	private DaoFactory() {
		this.factoryService = new HashMap<String, IDaoService>();
	}

	/**
	 * Create a only one instance of the DaoFactory class
	 * 
	 * @return a instance of the DaoFactory class
	 */
	public static DaoFactory getSingletonInstance() {
		if (daoFactory == null) {
			daoFactory = new DaoFactory();

			// Implementation of data access by Local File
			DaoRequestImpl daoRequestImpl = new DaoRequestImpl();
			// Add the implementation to dao factory
			daoFactory.addService(FILE, daoRequestImpl);
		}
		return daoFactory;
	}

	/**
	 * Add a implementation of IDaoService interface to factoryService map
	 * 
	 * @param name
	 *            service name
	 * @param service
	 *            implementation of IDaoService
	 */
	public void addService(String name, IDaoService service) {
		daoFactory.factoryService.put(name, service);
	}

	/**
	 * Get a implementation of IDaoService by a name
	 * 
	 * @param name
	 *            service name
	 * @return a implementation of IDaoService
	 */
	public IDaoService getService(String name) {
		IDaoService service = daoFactory.factoryService.get(name);
		return service;
	}
}
