package controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MainServer {

	private final int port = 50000;
	private ServerSocket svSock;
	private Socket sock;
	private ArrayList<ServerThread> connList;
	private ArrayList<Socket> sockList;
	private AdminProject adminP;

	public static void main(String[] args) {
		new MainServer();
	}
	
	public MainServer() {
		boolean isConnected = false;


		isConnected = init(isConnected);
		
		while (isConnected) {
			try {
				
				sock = svSock.accept();
				System.out.println("connection established");

				ServerThread svThread = new ServerThread(sock, adminP);
				sockList.add(sock);
				connList.add(svThread);
				svThread.start();

				// svThread.join();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Connection Error");
			}

		} // end while

		// unreachable
		// svSock.close();

	}

	public boolean init(boolean isConnected) {
		
		svSock = null;
		sock = null;

		System.out.println("Server Listening...");

		try {
			svSock = new ServerSocket(port);
			sockList = new ArrayList<Socket>();
			connList = new ArrayList<ServerThread>();
			adminP = new AdminProject();
			isConnected = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("init");
		}

		/*
		 * try { ReadObject(); } catch (IOException e2) { // TODO Auto-generated
		 * catch block e2.printStackTrace(); }
		 */
		return isConnected;
	}

	public void SaveObject() {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;

		try {

			fos = new FileOutputStream("object.dat");
			oos = new ObjectOutputStream(fos);

			// 해당 파일에 객체를 순차적으로 쓴다
			// oos.writeObject(userList);
			 oos.writeObject(adminP.getProjectList());
			System.out.println("Object saved.");

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			if (fos != null)
				try {
					fos.close();
				} catch (IOException e) {
				}
			if (oos != null)
				try {
					oos.close();
				} catch (IOException e) {
				}
		}

	}

	@SuppressWarnings("unchecked")
	public static void ReadObject() throws IOException {
		FileInputStream fis = null;
		ObjectInputStream ois = null;

		try {

			fis = new FileInputStream("object.dat");
			ois = new ObjectInputStream(fis);

			ois.readObject();
			// userList = (HashMap<String, String>) ois.readObject();
			// projectList = (HashMap<String, LinkedList>) ois.readObject();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fis != null)
				try {
					fis.close();
				} catch (IOException e) {
				}
			if (ois != null)
				try {
					ois.close();
				} catch (IOException e) {
				}
		}
	}

	public void exit() {

	}
}