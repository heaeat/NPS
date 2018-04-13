package model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

public class Category implements Serializable   {
	private String ctgName;
	private LinkedList<Choice> choiceList;
	private ArrayList<String> singleList;
	private ArrayList<String> errorList;
	private ArrayList<String> ifList;

	public Category(String ctgName) {
		this.ctgName = ctgName;
		this.choiceList = new LinkedList<Choice>();
		this.singleList = new ArrayList<String>();
		this.errorList = new ArrayList<String>();		this.ifList = new ArrayList<String>();
	}

	public void addChoice(Choice choice) {
		if (!(choice.isError() || choice.isSingle())) {
			this.choiceList.add(choice);
		}
	}

	public String getCtgName() {
		return this.ctgName;
	}

	public LinkedList<Choice> getChoiceList() {
		return choiceList;
	}

	public void addSingle(String single) {
		this.singleList.add(single);
	}

	public void addError(String error) {
		this.errorList.add(error);
	}

	public int getSingleNum() {
		return singleList.size();
	}

	public int getErrorNum() {
		return errorList.size();
	}

	public int getChoiceNum() {
		return choiceList.size();
	}

	public ArrayList<String> getSingleList() {
		return singleList;
	}

	public ArrayList<String> getErrorList() {
		return errorList;
	}

	public ArrayList<String> getIfList() {
		return ifList;
	}

	public void addIf(String ifString) {
		if (!ifList.contains(ifString)) {
			this.ifList.add(ifString);
		}
	}

}
