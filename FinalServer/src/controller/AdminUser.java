package controller;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminUser {

	private String ID;
	private String PW;
	private HashMap<String, String> userList;
	// <ID, PW>

	public AdminUser() {
		userList = new HashMap<String, String>();

		// test code
		userList.put("ADMIN", "0000");
		userList.put("1", "1");
		userList.put("2", "2");
		userList.put("3", "3");
		userList.put("201411317", "201411317");
		userList.put("201411304", "201411304");
	}

	public boolean login(String ID, String PW) {
		// TODO implement here
		boolean status = chkPW(true, ID, PW);
		if (status) {
			userList.put(ID, PW);
			this.ID = ID;
			this.PW = PW;
		}
		return status;
	}

	public boolean chgPW(String PW, String newPW) {
		boolean status = chkPW(false, PW, newPW);
		if (status) {
			userList.replace(ID, PW, newPW);
			this.PW = newPW;
		}

		return status;
	}

	public boolean chkAuth() {
		if (ID.equals("ADMIN")) {
			System.out.println("AUTH ACK");
			return true;

		} else {

			System.out.println("AUTH REJ");
			return false;
		}
	}

	public boolean chkPW(boolean flag, String str1, String str2) {
		// flag : login(true), chgPW(false)

		// login
		if (flag) {
			String present_pw = userList.get(str1); // or PW
			if (present_pw == null) {
				// not exist id in userList
				return false;

			} else if (!present_pw.equals(str2)) {
				// not equal pw in userList
				return false;

			} else {
				// login OK
				return true;

			}

		} else {
			// chgPW
			if (!PW.equals(str1)) {
				// not equal pw in userList
				return false;
			} else {
				return true;
			}
		}
	}

	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public String getPW() {
		return PW;
	}

	public void setPW(String pw) {
		PW = pw;
	}

}