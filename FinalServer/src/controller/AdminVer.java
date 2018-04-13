package controller;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

import model.Opinion;
import model.Version;

public class AdminVer {

	private Server server;
	private LinkedList<Version> tmpVerList;
	private Version tmpVer;
	private Opinion tmpOpi;

	public AdminVer(Server server) {
		this.server = server;
		tmpVerList = new LinkedList<Version>();
	}

	public boolean chkAuth(boolean flag, String ID, LinkedList tmpList, String verNum, String opinionNum) {
		// true(delV), false(delO)
		boolean status = false;
		this.tmpVerList = tmpList;
		String tmpVerNum = verNum;
		String tmpOpinionNum = opinionNum;

		if (flag) {
			// delV
			// verList, verNum
			ListIterator<Version> it = tmpVerList.listIterator();
			while (it.hasNext()) {
				Version tmpVer = (Version) it.next();
				if (tmpVer.getVerNum().equals(tmpVerNum)) {
					this.tmpVer = tmpVer;
					if (ID.equals(tmpVer.getWriter()))
						status = true;
					break;
				}
			}
			System.out.println("verNum " + tmpVer.getVerNum());
			return status;

		} else {
			// delOpi
			// verList, verNum, opinionID
			ListIterator<Version> it = tmpVerList.listIterator();
			while (it.hasNext()) {
				Version tmpVer = (Version) it.next();
				if (tmpVer.getVerNum().equals(tmpVerNum)) {
					this.tmpVer = tmpVer;
					break;
				}
			}

			int opNum = Integer.parseInt(tmpOpinionNum);
			ListIterator<Opinion> ito = tmpVer.getOpinionList().listIterator();
			for (Opinion op : tmpVer.getOpinionList()) {
				System.out.print(op.getOpinionNum() + " ");
				System.out.println("IDIDIDID : " + ID + " " + op.getOpinionID());
				if (op.getOpinionNum() == opNum) {
					this.tmpOpi = op;
					// System.out.println(tmpOpi.getContent()+
					// "!@#$%^&&^%$#$%^%$#@#$%^%$#");

					if (ID.equals(op.getOpinionID())) {
						status = true;
						System.out.println("지울게욤");
						break;
					}
				}

			}

			/*
			 * //여기로 안들어온다!!!!!!getopinionList..!!! int opNum =
			 * Integer.parseInt(tmpOpinionNum); while (it.hasNext()) { Opinion
			 * tmpO = (Opinion) ito.next();
			 * 
			 * System.out.println("IDIDIDID : " + ID + " " +
			 * tmpO.getOpinionID()); if (tmpO.getOpinionNum() == opNum) {
			 * this.tmpOpi = tmpO; // System.out.println(tmpOpi.getContent()+ //
			 * "!@#$%^&&^%$#$%^%$#@#$%^%$#");
			 * 
			 * if (ID.equals(tmpO.getOpinionID())) { status = true;
			 * System.out.println("지울게욤"); break; } } }
			 */
			System.out.println("status : " + status);
			return status;
		}
	}

	public LinkedList addVer(String id, Version version, LinkedList<Version> verList) {
		this.tmpVerList = (LinkedList<Version>) verList;
		version.setWriter(id);
		tmpVerList.add(version);

		Opinion tmpO = (Opinion) tmpVerList.getLast().getOpinionList().getLast();
		tmpO.setOpinionNum(tmpVerList.getLast().getOpinionList().size() - 1);
		tmpO.setOpinionID(id);

		return tmpVerList;
	}

	public LinkedList delVer() {
		this.tmpVerList.remove(this.tmpVer);
		return tmpVerList;
	}

	public LinkedList addOpinion(String id, LinkedList verList, String verNum, Opinion opinion) {
		this.tmpVerList = verList;
		this.tmpOpi = opinion;

		tmpOpi.setOpinionID(id);
		ListIterator<Version> it = tmpVerList.listIterator();

		while (it.hasNext()) {
			Version tmpVer = (Version) it.next();
			if (tmpVer.getVerNum().equals(verNum)) {
				this.tmpVer = tmpVer;
				if (this.tmpVer.getOpinionList().size() == 0) {
					tmpOpi.setOpinionNum(this.tmpVer.getOpinionList().size());
				} else {
					tmpOpi.setOpinionNum(this.tmpVer.getOpinionList().getLast().getOpinionNum() + 1);
				}
				this.tmpVer.getOpinionList().add(tmpOpi);
				break;
			}
		}
		return tmpVerList;
	}

	public LinkedList delOpinion() {
		ListIterator<Version> it = tmpVerList.listIterator();

		while (it.hasNext()) {

			Version tmpVer = (Version) it.next();
			for (Opinion op : this.tmpVer.getOpinionList()) {
				System.out.print(op.getContent() + " ");
			}
			System.out.println("================");

			this.tmpVer.getOpinionList().remove(tmpOpi);
			tmpVer = this.tmpVer;
			break;
		}
		return tmpVerList;
	}

	public File downloadFile(String verNum, LinkedList verList) {
		File file = null;
		this.tmpVerList = verList;

		ListIterator<Version> it = tmpVerList.listIterator();
		while (it.hasNext()) {
			Version tmpVer = (Version) it.next();
			if (tmpVer.getVerNum().equals(verNum)) {
				this.tmpVer = tmpVer;
				file = this.tmpVer.getFile();
				break;
			}
		}

		return file;
	}

	public void setVerList(LinkedList verList) {
		this.tmpVerList = verList;
	}

	public void sendOpinionList(LinkedList verList, String verNum) {
		this.tmpVerList = verList;

		Iterator<Version> iter = tmpVerList.iterator();
		while (iter.hasNext()) {
			Version ver = iter.next();
			if (ver.getVerNum().equals(verNum)) {
				server.sendFile1(ver.getOpinionList());
				break;
			}
		}

	}

}