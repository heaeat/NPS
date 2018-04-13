package controller;

import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;

import model.Opinion;
import model.Version;

public class ServerThread extends Thread {

	private AdminUser adminU;
	private AdminProject adminP;
	private AdminVer adminV;
	private Server server;

	private boolean status;
	private String projectName;

	public ServerThread(Socket sock, AdminProject adminP) {
		this.server = new Server(sock);
		this.adminP = adminP;

		adminU = new AdminUser();
		adminV = new AdminVer(server);
	}

	public void analyzeMSG(String msg) {
		// ¹ÞÀº ¸Þ½ÃÁö ºÐ¼®
		String projectName, verNum, ID, PW, newPW, opinionNum;
		Object obj = null;
		LinkedList tmpList;

		switch (msg) {
		case "/LGIN":
			// ID, PW
			ID = server.rcvFromClient();
			PW = server.rcvFromClient();

			if (adminU.login(ID, PW)) {
				server.sendACK();
			} else {
				server.sendREJ();
			}
			break;

		case "/CHPW":
			// PW, newPW
			PW = server.rcvFromClient();
			newPW = server.rcvFromClient();

			if (adminU.chgPW(PW, newPW)) {
				server.sendACK();
			} else {
				server.sendREJ();
			}
			break;

		case "/ADDP":
			// projectName
			projectName = server.rcvFromClient();
			if (adminU.chkAuth()) {
				adminP.setProjectList(adminP.addProject(projectName));
				server.sendACK();
			} else {
				server.sendREJ();
			}
			break;

		case "/DELP":
			// projectName
			projectName = server.rcvFromClient();
			if (adminU.chkAuth()) {
				adminP.delProject(projectName);
				server.sendACK();
			} else {
				server.sendREJ();
			}
			break;

		case "/ADDV":
			// projectName, Version°´Ã¼
			projectName = server.rcvFromClient();
			try {
				obj = server.rcvFile1();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // .rcvObjFromServer();

			ID = adminU.getID();
			tmpList = adminV.addVer(ID, (Version) obj, adminP.getVerList(projectName));
			adminP.setVerList(projectName, tmpList);
			server.sendACK();
			break;

		case "/DELV":
			// projectName, verNum
			projectName = server.rcvFromClient();
			verNum = server.rcvFromClient();
			ID = adminU.getID();
			tmpList = adminP.getVerList(projectName);

			boolean flag = adminV.chkAuth(true, ID, tmpList, verNum, null) || adminU.chkAuth();
			if (flag) {
				tmpList = adminV.delVer();
				adminP.setVerList(projectName, tmpList);
				server.sendACK();
			} else {
				server.sendREJ();
			}
			break;

		case "/ADDO":
			// projectName, verNum, opinion°´Ã¼
			projectName = server.rcvFromClient();
			verNum = server.rcvFromClient();
			try {
				obj = server.rcvFile1();
			} catch (IOException e) {
				e.printStackTrace();
			}

			ID = adminU.getID();
			tmpList = adminP.getVerList(projectName);
			tmpList = adminV.addOpinion(ID, tmpList, verNum, (Opinion) obj);
			adminP.setVerList(projectName, tmpList);

			server.sendACK();
			break;

		case "/DELO":
			// projectname, verNum, opinionNum
			projectName = server.rcvFromClient();
			verNum = server.rcvFromClient();
			opinionNum = server.rcvFromClient();
			System.out.println(opinionNum + "opinion#");
			ID = adminU.getID();
			tmpList = adminP.getVerList(projectName);
			boolean flag2 = false;

			// chkAdmin
			if (adminV.chkAuth(false, ID, tmpList, verNum, opinionNum) || adminU.chkAuth()) {
				flag2 = true;
			}

			if (flag2) {
				System.out.println("Áö¼ö ¾È´¨ACK");
				tmpList = adminV.delOpinion();
				adminP.setVerList(projectName, tmpList);
				server.sendACK();
			} else {
				System.out.println("Áö¼ö ¾È´¨REJ");
				server.sendREJ();
			}
			break;

		case "/RCVP":
			adminP.sendProjectList(server);
			break;

		case "/RCVV":
			// projectName
			projectName = server.rcvFromClient();
			adminP.sendVerList(projectName, server);
			break;

		case "/RCVO":
			// projectName, verNum
			tmpList = adminP.getVerList(server.rcvFromClient());
			verNum = server.rcvFromClient();

			adminV.sendOpinionList(tmpList, verNum);
			break;

		case "/DOWN":
			// projectName, verNum
			projectName = server.rcvFromClient();
			verNum = server.rcvFromClient();

			tmpList = adminP.getVerList(projectName);
			server.sendFile1(adminV.downloadFile(verNum, tmpList));
			break;

		case "/EXIT":
			// break
			break;

		default:
			break;
		}

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		String msg = null;
		System.out.println("Listening...Run..");

		while ((msg = server.rcvFromClient()) != null) {
			if (msg.compareTo("/EXIT") != 0) {
				analyzeMSG(msg);
			}

			try {
				sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				System.out.println("client out!");
			}
		}
	}

}
