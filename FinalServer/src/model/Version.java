package model;



import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;

public class Version implements Serializable{

	private String verNum;
	private int choiceNum;
	private int consNum;
	private File file;
	private LinkedList<Category> ctgList;
	private int caseNum;
	private LinkedList<Opinion> opinionList;
	private String writer;
	private HashMap<String,Integer> nothingHash;
	private HashMap<String,Integer> tempHash;
	private boolean caseFlag;
	
	public Version() {
		this.ctgList = new LinkedList<Category>();
		this.caseNum = 0;
		this.verNum = null;
		this.opinionList = new LinkedList<Opinion>();
		this.nothingHash = new HashMap<String,Integer>();
		this.tempHash = new HashMap<String,Integer>();
		this.caseFlag = false;
	}

	public Version(String verNum) {
		this.ctgList = new LinkedList<Category>();
		this.caseNum = 0;
		this.verNum = verNum;
		this.opinionList = new LinkedList<Opinion>();
		this.nothingHash = new HashMap<String,Integer>();
		this.tempHash = new HashMap<String,Integer>();
		this.caseFlag = false;
	}

	public Version(String verNum,File file) {
		this.ctgList = new LinkedList<Category>();
		this.caseNum = 0;
		this.verNum = verNum;
		this.file = file;
		this.opinionList = new LinkedList<Opinion>();
		this.nothingHash = new HashMap<String,Integer>();
		this.tempHash = new HashMap<String,Integer>();
		this.caseFlag = false;
	}
	
	public int getCtgNum(){
		return this.ctgList.size();
	}


	public int getChoiceNum() {
		// TODO implement here
		return choiceNum;
	}

	public int getConsNum() {
		// TODO implement here
		return consNum;
	}

	public void addCategory(Category ctg) {
		this.ctgList.add(ctg);
	}

	public void addCase() {
		this.caseNum++;
	}

	public void addCase(int caseNum) {
		this.caseNum += caseNum;
	}

	public int getCaseNum() {
		return this.caseNum;
	}

	public LinkedList<Category> getCtgList() {
		return this.ctgList;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public void setChoiceNum(int choiceNum) {
		this.choiceNum = choiceNum;
	}

	public void setConsNum(int consNum) {
		this.consNum = consNum;
	}

	public void setCtgList(LinkedList<Category> ctgList) {
		this.ctgList = ctgList;
	}

	public void setCaseNum(int caseNum) {
		this.caseNum = caseNum;
	}

	public String getVerNum() {
		return verNum;
	}

	public void setVerNum(String verNum) {
		this.verNum = verNum;
	}
	public void addChoice(){
		this.choiceNum++;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public LinkedList<Opinion> getOpinionList() {
		return opinionList;
	}

	public void setOpinionList(LinkedList<Opinion> opinionList) {
		this.opinionList = opinionList;
	}

	public HashMap<String, Integer> getNothingHash() {
		return nothingHash;
	}

	public void setNothingHash(HashMap<String, Integer> nothingHash) {
		this.nothingHash = nothingHash;
	}

	public HashMap<String, Integer> getTempHash() {
		return tempHash;
	}

	public void setTempHash(HashMap<String, Integer> tempHash) {
		this.tempHash = tempHash;
	}

	public boolean getCaseFlag() {
		return this.caseFlag;
	}
	
	public void setCaseFlag(){
		this.caseFlag= true;
	}
	
}