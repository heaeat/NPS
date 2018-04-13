package controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import model.Version;

public class AdminProject {

	// projectList가 필요한지 확인해보자!
	private HashMap<String, LinkedList<Version>> projectList;
	private Client client;
	private HashSet projectNameSet;

	public AdminProject(Client client) {
		this.client = client;
		this.projectNameSet = null;
		this.projectList = null;
	}

	public boolean addProject(String projectName) {
		client.sendToServer("/ADDP");
		client.sendToServer(projectName);
		String msg = client.rcvFromServer();

		if (msg.equals("/ACK")) {
			return true;
		} else {
			return false;
		}
	}

	public boolean delProject(String projectName) {
		client.sendToServer("/DELP");
		client.sendToServer(projectName);
		String msg = client.rcvFromServer();
		if (msg.equals("/ACK")) {
			return true;
		} else {
			return false;
		}
	}

	public HashSet rcvProjectList() {
		
		try {
			client.sendToServer("/RCVP");
			this.projectNameSet = (HashSet) client.rcvObjFromServer();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.projectNameSet;
	}

}