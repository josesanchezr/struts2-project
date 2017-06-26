package com.empresa.struts.actions;

import java.util.List;

import org.apache.log4j.Logger;

import com.empresa.struts.dao.DaoFactory;
import com.empresa.struts.dao.IDaoService;
import com.empresa.struts.models.Request;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Store the request to a new institution or branch
 * 
 * @author joseluis
 *
 */
public class RequestAction extends ActionSupport {
	private static final long serialVersionUID = 1L;

	/**
	 * Logger used to log information
	 */
	final static Logger logger = Logger.getLogger(RequestAction.class);

	/**
	 * Store the information of request done by user
	 */
	private Request requestBean;

	/**
	 * It's used by the data access layer to get information of institutions
	 */
	private IDaoService iDaoService;

	/**
	 * Execute the saverequest action
	 */
	public String execute() throws Exception {
		// Validate if requestBean have information
		if (requestBean != null) {
			// Initialize the dao factory used to get the data access layer
			DaoFactory daoFactory = DaoFactory.getSingletonInstance();
			// Get the dao service to access to the information stored into a
			// file
			iDaoService = daoFactory.getService(DaoFactory.FILE);
			// Validate if it's a headquarter
			if (requestBean.getHeadquarter().equals("Yes")) {
				// Get the institution by its name
				Request requestHeadquarter = iDaoService.getHeadquarter(requestBean.getName());
				// If the institution exists already, then it does not store it
				// into file
				if (requestHeadquarter != null) {
					logger.info("This headquarter already exists");
					return INPUT;
				}
				// Store the institution into file
				return saveRequest(requestBean);
			} else {
				// If it isn't a headquarter, then get the list of institutions
				// with same name
				List<Request> requests = iDaoService.getInstitutionsByName(requestBean.getName());
				for (Request request : requests) {
					// Validate if the institution have same country and city
					if (request.getCountry().equals(requestBean.getCountry())
							&& request.getCity().equals(requestBean.getCity())) {
						logger.info("Already exists a branch with the same country and city");
						return INPUT;
					}
				}
				// Store the institution into file
				return saveRequest(requestBean);
			}
		}
		return SUCCESS;
	}

	/**
	 * Get name of request for to use as key to store it
	 * 
	 * @param request
	 *            information of institution
	 * @return name of request
	 */
	private String getRequestName(Request request) {
		return request.getName() + "-" + request.getCountry() + "-" + request.getCity();
	}

	/**
	 * Store the information of institution
	 * 
	 * @param request
	 *            information of institution
	 * @return success if stored the information, else return input
	 */
	private String saveRequest(Request request) {
		String name = getRequestName(request);
		String save = iDaoService.save(request, name);
		if (save.equals("error")) {
			logger.error("Error saving request");
			return INPUT;
		}
		return SUCCESS;
	}

	/**
	 * Get information of request sent by user
	 * 
	 * @return information of request
	 */
	public Request getRequestBean() {
		return requestBean;
	}

	/**
	 * Set information of request sent by user
	 * 
	 * @param requestBean
	 *            information of request
	 */
	public void setRequestBean(Request requestBean) {
		this.requestBean = requestBean;
	}
}
