package controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class MainController {

	private Socket sock;
	private InetAddress address;
	private Client client;
	private AdminUser adminU;
	private AdminProject adminP;
	private AdminVer adminV;

	public MainController() {
		init();
		client.start();

		/*
		 * test code while(true){ adminU.login("1", "2"); adminU.login("ADMIN",
		 * "0000"); adminP.addProject("hello world");
		 * adminP.delProject("hello world");
		 * 
		 * adminP.addProject("abc"); //int opinionNum, String opinionID, Date
		 * date, String content) Opinion o = new Opinion();
		 * 
		 * adminV.uploadVer("1.0", "hello hello");
		 * 
		 * System.out.println("Bye!"); break; }
		 */
	}

	public void init() {
		try {
			// 성규형 ip "172.16.33.111"
			// 지수 ip 172.16.50.83
			// 지수 최근 "172.16.49.75
			this.address = InetAddress.getLocalHost();
			this.sock = new Socket("localhost", 50000);
			System.out.println("Client Connected");

			this.client = new Client(sock);
			adminU = new AdminUser(client);
			adminP = new AdminProject(client);
			adminV = new AdminVer(client);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public Client getClient() {
		return client;
	}

	public AdminUser getAdminU() {
		return adminU;
	}

	public AdminProject getAdminP() {
		return adminP;
	}

	public AdminVer getAdminV() {
		return adminV;
	}

}