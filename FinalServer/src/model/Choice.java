package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class Choice implements Serializable   {
	private String choiceName;
	private ArrayList<Constraints> consList;
	private boolean hasProp;
	private boolean hasSingle;
	private boolean hasError;
	private boolean hasIf;
	private ArrayList<String> propList;
	private ArrayList<String> ifList;
	private ArrayList<String> elseList;
	private boolean hasElse;
	private int consNum;

	public Choice(String choiceName) {
		this.choiceName = choiceName;
		this.consList = new ArrayList<Constraints>();
		this.propList = new ArrayList<String>();
		this.ifList = new ArrayList<String>();
		this.elseList = new ArrayList<String>();
		this.hasError = false;
		this.hasSingle = false;
		this.hasProp = false;
		this.hasElse = false;
		this.consNum = 0;
	}

	public String getChoiceName() {
		return choiceName;
	}

	public void addCons(Constraints cons) {
		this.consList.add(cons);
		this.consNum++;
	}

	public void addIf(String ifString) {
		if (!(this.hasError || this.hasSingle)) {
			this.ifList.add(ifString);
			System.out.println("ifString: "+ifString);
			this.hasIf = true;
		}
	}

	public void addElse(String elseString) {
		this.elseList.add(elseString);
		this.hasElse = true;
	}

	public void addProp(String propString) {
		if (!(this.hasError || this.hasSingle)) {
			this.propList.add(propString);
			this.hasProp = true;
		}
	}

	public ArrayList<Constraints> getConsList() {
		return consList;
	}

	public boolean isProp() {
		return hasProp;
	}

	public boolean isSingle() {
		return hasSingle;
	}

	public boolean isError() {
		return hasError;
	}
	
	public void setIsProp() {
		this.hasProp = true;
	}

	public void setIsSingle() {
		this.hasSingle = true;
	}

	public void setIsError() {
		this.hasError = true;
	}

	public void setIsElse() {
		this.hasElse = true;
	}

	public ArrayList<String> getPropList() {
		return propList;
	}

	public ArrayList<String> getIfList() {
		return ifList;
	}

	public boolean isIf() {
		return this.hasIf;
	}

	public boolean isElse() {
		return this.hasElse;
	}

	public ArrayList<String> getElseList() {
		return elseList;
	}

	public int getConsNum() {
		return this.consNum;
	}
}
