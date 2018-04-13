package controller;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import model.Opinion;
import model.Version;

public class AdminProject {

	// private Server server = null;
	private String projectName;
	private HashMap<String, LinkedList<Version>> projectList;
	// <projectName, verList>

	public AdminProject() {
		// TODO implement here
		// this.server = server;
		projectList = new HashMap<String, LinkedList<Version>>();

		// testcode
		projectList.put("1", new LinkedList<Version>());
		projectList.put("2", new LinkedList<Version>());

		LinkedList<Version> v = new LinkedList<Version>();
		v.add(new Version("0", new File("test.txt")));

		Version ver = new Version("1.0");
		ver.setChoiceNum(3);
		ver.setCaseNum(1234);
		// ver.setCtgNum(100);
		ver.setFile(new File("test.txt"));
		LinkedList<Opinion> n = new LinkedList<Opinion>();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();

		Opinion opi = new Opinion("밍규바보바보바보바보");
		opi.setOpinionID("조민규");
		opi.setDate(dateFormat.format(date));
		opi.setOpinionNum(0);

		Opinion opi2 = new Opinion("안녕하시비까");
		opi.setOpinionID("지슈지슈바보바보똥개");
		opi.setDate(dateFormat.format(date));
		opi.setOpinionNum(1);

		n.push(opi);
		n.push(opi2);

		v.add(new Version("1.0"));

		projectList.put("3", v);
		//////

		LinkedList<Version> vv = new LinkedList<Version>();
		Version ver2 = new Version("1.0");
		ver2.setChoiceNum(3);
		ver2.setCaseNum(1234);
		// ver2.setCtgNum(100);
		ver2.setFile(new File("test.txt"));

		Version ver3 = new Version("2.0");
		ver3.setChoiceNum(3);
		ver3.setCaseNum(1234);
		// ver3.setCtgNum(100);
		ver3.setFile(new File("test.txt"));

		LinkedList<Opinion> n2 = new LinkedList<Opinion>();
		ver2.setOpinionList(n2);
		vv.add(ver2);
		vv.add(ver3);

		projectList.get("1").add(ver);
		projectList.get("1").add(ver2);
		projectList.put("4", vv);
	}

	public synchronized HashMap addProject(String projectName) {
		LinkedList<Version> tmpVerList = new LinkedList<Version>();
		projectList.put(projectName, tmpVerList);
		return projectList;
	}

	public synchronized void delProject(String projectName) {
		projectList.remove(projectName);
	}

	public void sendProjectList(Server server) {
		server.sendObjToClient(new HashSet(projectList.keySet()));
	}

	public void sendVerList(String projectName, Server server) {
		server.sendFile1(projectList.get(projectName));
	}

	// getters and setters
	public HashMap getProjectList() {
		return projectList;
	}

	public void setProjectList(HashMap<String, LinkedList<Version>> projectList) {
		this.projectList = projectList;
	}

	public LinkedList getVerList(String projectName) {
		this.projectName = projectName;
		return projectList.get(projectName);
	}

	public void setVerList(String projectName, LinkedList<Version> verList) {
		this.projectList.put(projectName, verList);

	}

}