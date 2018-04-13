package controller;

public class AdminUser {

	private String ID;
	private String PW;
	private Client client;

	public AdminUser(Client client) {
		this.ID = null;
		this.PW = null;
		this.client = client;
	}

	public boolean login(String ID, String PW) {
		this.ID = ID;
		this.PW = PW;
		client.sendToServer("/LGIN");
		client.sendToServer(ID);
		client.sendToServer(PW);
		String msg = client.rcvFromServer();
		if (msg.equals("/ACK")) {
			return true;
		} else {
			return false;
		}
	}

	public int chgPW(String PW, String PW1, String PW2) {
		boolean same = isSame(PW1, PW2);

		if (same) {
			client.sendToServer("/CHPW");
			client.sendToServer(PW);
			client.sendToServer(PW1);
			
			String msg = client.rcvFromServer();
			if (msg.equals("/ACK")) {
				return 0;
			}else{
				return 2;
			}

		} else {
			return 1;	// "새 비밀번호와 새 비밀번호 확인이 다릅니다!"
			
		}

	}

	public boolean isSame(String PW1, String PW2) {
		if (PW1.equals(PW2)) {
			return true;
		} else {
			return false;
		}
	}

	public void exit() {
		client.sendToServer("/EXIT");
		System.exit(0);
	}

	public String getID() {
		// TODO Auto-generated method stub
		return ID;
	}
}