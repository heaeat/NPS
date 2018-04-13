package model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Opinion implements Serializable {

	private int opinionNum;
	private String opinionID;
	private String date;
	private String content;

	public Opinion(String content) {
		// TODO implement here
		this.opinionNum = -1;
		this.opinionID = null;

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		this.date = dateFormat.format(date);
		this.content = content;
	}

	public String getOpinionID() {
		return opinionID;
	}

	public void setOpinionID(String opinionID) {
		this.opinionID = opinionID;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setOpinionNum(int opinionNum) {
		this.opinionNum = opinionNum;
	}

	public int getOpinionNum() {
		return opinionNum;
	}

}