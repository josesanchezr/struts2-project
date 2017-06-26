package com.empresa.struts.dao.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.empresa.struts.dao.IDaoService;
import com.empresa.struts.models.Request;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * Implementation of IDaoService interface to store information into local file
 * 
 * @author joseluis
 *
 */
public class DaoRequestImpl implements IDaoService {

	/**
	 * It's used to get the relative path to the file
	 */
	static final ClassLoader loader = DaoRequestImpl.class.getClassLoader();

	/**
	 * Constructor of class
	 */
	public DaoRequestImpl() {
	}

	/**
	 * Get a map with all the institutions stored
	 * 
	 * @return a map with information of institutions
	 */
	public Map<String, Request> getAll() {
		String json = readFileJson();
		Map<String, Request> requestMap = new HashMap<String, Request>();
		if (!"".equals(json)) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			Type listType = new TypeToken<Map<String, Request>>() {
			}.getType();
			requestMap = (Map<String, Request>) gson.fromJson(json, listType);
		}
		return requestMap;
	}

	/**
	 * Get a list with all the institution stored
	 * 
	 * @return a list with information of institutions
	 */
	public List<Request> getAllAsList() {
		Map<String, Request> requestMap = getAll();
		List<Request> requests = new ArrayList<Request>();
		if (requestMap != null && !requestMap.isEmpty()) {
			for (Entry<String, Request> e : requestMap.entrySet()) {
				requests.add(e.getValue());
			}
		}
		return requests;
	}

	/**
	 * Get a list with information of institutions with the same name
	 * 
	 * @param name
	 *            institution name
	 * @return list of institutions
	 */
	public List<Request> getInstitutionsByName(String name) {
		Map<String, Request> requestMap = getAll();
		List<Request> requests = new ArrayList<Request>();
		if (requestMap != null && !requestMap.isEmpty()) {
			for (Entry<String, Request> e : requestMap.entrySet()) {
				Request request = e.getValue();
				if (request.getName().equals(name)) {
					requests.add(request);
					break;
				}
			}
		}
		return requests;
	}

	/**
	 * Get a institution headquarter
	 * 
	 * @param name
	 *            institution name
	 * @return institution headquarter
	 */
	public Request getHeadquarter(String name) {
		Map<String, Request> requestMap = getAll();
		if (requestMap != null && !requestMap.isEmpty()) {
			for (Entry<String, Request> e : requestMap.entrySet()) {
				Request request = e.getValue();
				if (request.getName().equals(name) && request.getHeadquarter().equals("Yes")) {
					return request;
				}
			}
		}
		return null;
	}

	/**
	 * Get the information of institution by its name
	 * 
	 * @param name
	 *            name of institution
	 * @return information of institution
	 */
	public Request getRequest(String name) {
		Map<String, Request> requestMap = getAll();
		Request currentRequest = requestMap.get(name);
		return currentRequest;
	}

	/**
	 * Store a institution
	 * 
	 * @param request
	 *            information of institution
	 * @param name
	 *            name of institution
	 * @return success if stored the information of institution into file
	 */
	public String save(Request request, String name) {
		Map<String, Request> requestMap = getAll();
		if (requestMap == null || requestMap.isEmpty()) {
			requestMap = new HashMap<String, Request>();
		}

		requestMap.put(name, request);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(requestMap);
		writeFileJson(json);
		return "success";
	}

	/**
	 * Delete a institution
	 * 
	 * @param name
	 *            institution name
	 * @return success if deleted the institution, else return error
	 */
	public String delete(String name) {
		Map<String, Request> requestMap = getAll();
		if (requestMap.size() > 0) {
			Request request = requestMap.remove(name);
			if (request != null) {
				return "success";
			}
		}
		return "error";
	}

	/**
	 * Read the information of a file and return as a string in json format
	 * 
	 * @return string in json format
	 */
	public String readFileJson() {
		URL folder = loader.getResource("data");

		File file = null;
		String json = null;
		BufferedReader br = null;
		try {
			file = new File(folder.getPath() + "file.json");
			if (file.exists()) {
				br = new BufferedReader(new FileReader(file));

				StringBuilder sb = new StringBuilder();
				String line = br.readLine();

				while (line != null) {
					sb.append(line);
					sb.append(System.lineSeparator());
					line = br.readLine();
				}
				json = sb.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return json;
	}

	/**
	 * Write the information in json format into a file
	 * 
	 * @param json
	 *            information in json format
	 */
	public void writeFileJson(String json) {
		URL folder = loader.getResource("data");
		File file = null;
		FileWriter fileWriter = null;
		try {
			file = new File(folder.getPath() + "file.json");
			file.createNewFile();
			fileWriter = new FileWriter(file);
			fileWriter.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fileWriter != null) {
					fileWriter.flush();
					fileWriter.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
