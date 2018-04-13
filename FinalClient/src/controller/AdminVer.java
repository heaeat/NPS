package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import controller.AdminCase;
import controller.Client;
import model.Category;
import model.Choice;
import model.Constraints;
import model.Opinion;
import model.Version;

public class AdminVer {

	private LinkedList<Version> verList;
	private LinkedList<Opinion> opinionList;
	private Client client;
	private Version tempVer;

	public AdminVer(Client client) {
		this.client = client;
		this.verList = null;
		this.opinionList = null;
		this.tempVer = null;
	}

	public boolean addVer(File file) {
		String fileName = file.getName();
		String extension = fileName.substring(fileName.lastIndexOf("."), fileName.length());
		if (!extension.equals(".txt")) {
			return false;
		} else {
			return true;
		}

	}

	public Version analyzeFile(String fileName) throws IOException {

		Version ver = new Version("1.0");

		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		String str = null;
		Category ctg = null;
		Choice choice = null;
		String ifStr = null;
		while ((str = reader.readLine()) != null) {

			if (str.contains(":")) {

				// 앞과 뒤의 모든 공백을 지우는 코드
				str = str.trim();

				// :을 기준으로 str을 분리하기
				String strArr[] = str.split(":");
				ctg = new Category(strArr[0]);
				ver.addCategory(ctg);
			} else if (str.contains(".")) {

				// 앞과 뒤의 모든 공백을 지우는 코드
				str = str.trim();
				String strArr[] = str.split("\\.");
				choice = new Choice(strArr[0]);
				ver.addChoice();
				if (strArr.length > 1) {
					str = strArr[1].trim();
					System.out.println(str);
					Pattern p = Pattern.compile("\\[(.*?)\\]");
					Matcher m = p.matcher(str);
					boolean addFlag = false;

					while (m.find()) {
						// []를 기준으로 []사이에 있는 것 들을 분리하여 넣는다
						choice.addCons(new Constraints(m.group(1)));
						//System.out.println(m.group(1));
						if (m.group(1).contains("property")) {

							String strArr2[] = m.group(1).split("property ");
							choice.addProp(strArr2[1]);
							addFlag = true;
						} else if (m.group(1).contains("single")) {
							choice.setIsSingle();
							ctg.addSingle(choice.getChoiceName() + ": " + m.group(1));
						} else if (m.group(1).contains("error")) {
							choice.setIsError();
							ctg.addError(choice.getChoiceName() + ": " + m.group(1));
						} else if (m.group(1).contains("if")) {
							String strArr2[] = m.group(1).split("if ");
							addFlag = true;
							ifStr = strArr2[1];
							choice.addIf(ifStr);
							ctg.addIf(ifStr);

							ver.getNothingHash().put(ifStr, 0);
							ver.getTempHash().put(ifStr, 0);

						}

					}

					if (addFlag) {
						ctg.addChoice(choice);
					}
					addFlag = false;
				} else {
					ctg.addChoice(choice);
				}

			}

		}		
		this.tempVer = ver;
		reader.close();
		return ver;
	}

	public void makeCase(Version ver) throws IOException {

		countSingleError(ver);

		Iterator<Category> it = ver.getCtgList().iterator();
		
		while(it.hasNext()){
			Category ctg = it.next();
			Iterator<Choice> it0 = ctg.getChoiceList().iterator();
			if(it0.hasNext()){
				Choice choice = it0.next();
				if(choice.isError()!=true&&choice.isSingle()!=true&&choice.isProp()==true&& choice.isIf()==true){					System.out.println(choice.getChoiceName()+"AAAA");
					ver.setCaseFlag();
				}
				
			}
		}		
		int size = ver.getCtgList().size();
		int[] pointerArr = new int[size];
		LinkedList<Choice>[] choiceListArr = new LinkedList[size];
		for (int i = 0; i < size; i++) {
			pointerArr[i] = 0;
			choiceListArr[i] = ver.getCtgList().get(i).getChoiceList();
		}
		BufferedWriter bw = new BufferedWriter(new FileWriter("temp.txt"));
		makeTmpFile(ver, bw); /// write single and error
		makeTmpFile(pointerArr, choiceListArr, size, size, ver, bw); // write
																		// else
		this.tempVer.setFile(new File("temp.txt"));
		bw.close();
	}

	public void countSingleError(Version ver) {
		Iterator<Category> iter = ver.getCtgList().iterator();

		while (iter.hasNext()) {
			Category ctg = iter.next();
			Choice c = null;
			ver.addCase(ctg.getErrorNum());
			ver.addCase(ctg.getSingleNum());
			if (ctg.getChoiceList().size() == 0) {
				ctg.addChoice(new Choice("Nothing"));
			}
			Iterator<Choice> iter1 = ctg.getChoiceList().iterator();
			boolean ifFlag = true;
			String str = "";

			while (iter1.hasNext()) {
				c = iter1.next();

				if (!c.isIf()) {
					ifFlag = false;
				}

			}

			if (ifFlag) {
				for (int i = 0; i < c.getIfList().size(); i++) {
					if (!str.contains(c.getIfList().get(i))) {
						str += c.getIfList().get(i) + " ";
					}
				}

				Choice tempChoice = new Choice("Nothing");
				str = str.substring(0, str.length() - 1);
				tempChoice.setIsElse();
				tempChoice.addElse(str);
				ctg.addChoice(tempChoice);
			}
		}
	}
	private void makeTmpFile(Version ver, BufferedWriter bw) throws IOException {
		String str = "";

		Iterator<Category> iter = ver.getCtgList().iterator();
		while (iter.hasNext()) {
			Category ctg = iter.next();
			if (ctg.getErrorNum() != 0) {

				for (int i = 0; i < ctg.getErrorNum(); i++) {
					// str = "error: ";
					str += ctg.getErrorList().get(i);

					System.out.println(str);
					bw.write(str);
					bw.write("\r\n");
					str = "";
				}

			}

			if (ctg.getSingleNum() != 0) {

				for (int i = 0; i < ctg.getSingleNum(); i++) {
					// str = "single: ";
					str += ctg.getSingleList().get(i);
					System.out.println(str);
					bw.write(str);
					bw.write("\r\n");
					str = "";
				}

			}
		}

	}

	private void makeTmpFile(int[] pointerArr, LinkedList<Choice>[] choiceListArr, int totalSize, int idx, Version ver,
			BufferedWriter bw) {

		while (pointerArr[totalSize - idx] < choiceListArr[totalSize - idx].size()) {
			if (idx == 1) {

				AdminCase c = new AdminCase();
				boolean flag = true;

				ArrayList<String> propList = new ArrayList<String>();
				ArrayList<String> ifList = new ArrayList<String>();
				ArrayList<String> elseList = new ArrayList<String>();
				HashMap<String, Integer> tempHash = new HashMap<>();

				for (int j = 0; j < totalSize; j++) {

					c.getChoiceList().add(choiceListArr[j].get(pointerArr[j]));
					if (choiceListArr[j].get(pointerArr[j]).isProp()) {
						Iterator<String> iter = choiceListArr[j].get(pointerArr[j]).getPropList().iterator();
						while (iter.hasNext()) {

							String str = iter.next();
							propList.add(str);

							if (!tempHash.keySet().contains(str)) {
								tempHash.put(str, 0);
							}

						}
					}

					if (choiceListArr[j].get(pointerArr[j]).isIf()) {
						Iterator<String> iter = choiceListArr[j].get(pointerArr[j]).getIfList().iterator();
						while (iter.hasNext()) {
							String str = iter.next();
							ifList.add(str);

						}
					}

					if (choiceListArr[j].get(pointerArr[j]).isElse()) {
						Iterator<String> iter = choiceListArr[j].get(pointerArr[j]).getElseList().iterator();
						while (iter.hasNext()) {
							String str = iter.next();
							elseList.add(str);
						}
					}
				}


				for (int x = 0; x < ifList.size(); x++) {
					flag = flag & isContain(ifList.get(x), propList);
				}

				if (ver.getCaseFlag() == true) {
					for (int i = 0; i < ifList.size(); i++) {
						if (tempHash.keySet().contains(ifList.get(i))) {
							tempHash.put(ifList.get(i), tempHash.get(ifList.get(i)) + 1);
						}
					}

					Iterator<String> itHash = tempHash.keySet().iterator();
					boolean tempFlag = true;
					while (itHash.hasNext()) {
						if (tempHash.get(itHash.next()) == 0) {
							tempFlag = false;
							break;
						}
					}
					if (tempFlag == true) {
						flag = false;
					}
					
				}
				
				if (flag) {

					boolean flag1 = !flag;

					if (ver.getCaseFlag() == false) {

						for (int x = 0; x < propList.size(); x++) {
							flag1 = flag1 | isContain(propList.get(x), elseList);
						}
					}
					if (!flag1) {
						Iterator<Choice> iter = c.getChoiceList().iterator();
						String str = "";
						while (iter.hasNext()) {
							str += "[ " + iter.next().getChoiceName() + " ]" + "    ";
						}
						ver.addCase();
						// System.out.println();
						try {

							bw.write(str);
							bw.write("\r\n");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

				}

				pointerArr[totalSize - idx]++;

			} else {
				makeTmpFile(pointerArr, choiceListArr, totalSize, idx - 1, ver, bw);
				pointerArr[totalSize - idx + 1] = 0;
				pointerArr[totalSize - idx]++;
			}
		}

	}

	public boolean uploadVer(String projectName, Version ver) {
		client.sendToServer("/ADDV");
		client.sendToServer(projectName);
		client.sendFile1(ver);

		String msg = client.rcvFromServer();
		if (msg.equals("/ACK")) {
			return true;
		} else {
			return false;
		}
	}

	public boolean delVer(String projectName, String verNum) {
		client.sendToServer("/DELV");
		client.sendToServer(projectName);
		client.sendToServer(verNum);

		String msg = client.rcvFromServer();
		if (msg.equals("/ACK")) {
			return true;
		} else {
			return false;
		}
	}

	public void addOpinion(Opinion opinion, String projectName, String verNum) {

		client.sendToServer("/ADDO");
		client.sendToServer(projectName);
		client.sendToServer(verNum);
		client.sendFile1(opinion);

		client.rcvFromServer();
	}

	public boolean delOpinion(String projectName, String verNum, String opinionNum) {
		client.sendToServer("/DELO");
		client.sendToServer(projectName);
		client.sendToServer(verNum);
		client.sendToServer(opinionNum);
		String msg = client.rcvFromServer();
		if (msg.equals("/ACK")) {
			return true;
		} else {
			return false;
		}
	}

	public File downloadFile(String projectName, String verNum) {
		File file = null;
		client.sendToServer("/DOWN");
		client.sendToServer(projectName);
		client.sendToServer(verNum);

		try {
			file = (File) client.rcvFile1();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return file;
	}

	public LinkedList<Version> rcvVerList(String projectName) {

		try {
			client.sendToServer("/RCVV");
			client.sendToServer(projectName);
			this.verList = (LinkedList<Version>) client.rcvFile1();
			// this.verList = (LinkedList<Version>) client.rcvObjFromServer();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.verList;
	}

	public LinkedList<Opinion> rcvOpinionList(String projectName, String verNum) {

		try {
			client.sendToServer("/RCVO");
			client.sendToServer(projectName);
			client.sendToServer(verNum);
			this.opinionList = (LinkedList<Opinion>) client.rcvFile1();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.opinionList;
	}

	private boolean isContain(String str, ArrayList<String> arr) {
		if (arr.contains(str)) {
			return true;
		}
		return false;
	}

	public Version getTempVer() {
		return tempVer;
	}

	public void setTempVer(Version tempVer) {
		this.tempVer = tempVer;
	}
}
