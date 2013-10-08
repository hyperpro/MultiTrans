package com.fudan.sonic.fileCode;

public class NewFile {
	private int fileSize;
	private String fileName;
	private int offset1;
	private int offset2;
	private byte[] fileContent;
	
	public int getFileSize(){
		return fileSize;
	}
	
	public void setFileSize(int fileSize){
		this.fileSize = fileSize;
	}
	
	public String getFileName() {
		return fileName; 
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public int getOffset1() {
		return offset1;
	}
	
	public void setOffset1(int offset1){
		this.offset1 = offset1;
	}
	
	public int getOffset2() {
		return offset2;
	}
	
	public void setOffset2(int offset2) {
		this.offset2 = offset2;
	}
	
	public byte[] getFileContent() {
		return fileContent;
	}
	
	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}

}
