package com.fudan.sonic.fileCode;

public class NewFile {
	
	private String userName; //True userName or BroadCast
	private int messageSize;
	private byte[] messageContent;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getMessageSize() {
		return messageSize;
	}
	public void setMessageSize(int messageSize) {
		this.messageSize = messageSize;
	}
	public byte[] getMessageContent() {
		return messageContent;
	}
	public void setMessageContent(byte[] messageContent){
		this.messageContent = messageContent;
	}
}
