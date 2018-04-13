package controller;

import java.util.ArrayList;

import model.Choice;

public class AdminCase {
	private ArrayList<Choice> choiceList;
	private boolean hasProp;
	private boolean hasSingle;
	private boolean hasError;
	private ArrayList<String> propList;
	
	public AdminCase(){
		this.choiceList = new ArrayList<Choice>();
		this.hasProp = false;
		this.hasSingle = false;
		this.hasError = false;
		this.propList = new ArrayList<String>();
	}

	public ArrayList<Choice> getChoiceList() {
		return choiceList;
	}

	public void setChoiceList(ArrayList<Choice> choiceList) {
		this.choiceList = choiceList;
	}

	public boolean isProp() {
		return hasProp;
	}

	public void setHasProp(boolean hasProp) {
		this.hasProp = hasProp;
	}

	public boolean isSingle() {
		return hasSingle;
	}

	public void setHasSingle(boolean hasSingle) {
		this.hasSingle = hasSingle;
	}

	public boolean isError() {
		return hasError;
	}

	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}

	public ArrayList<String> getPropList() {
		return propList;
	}

	public void setPropList(ArrayList<String> propList) {
		this.propList = propList;
	}
	
}
