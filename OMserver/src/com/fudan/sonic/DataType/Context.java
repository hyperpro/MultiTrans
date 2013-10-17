package com.fudan.sonic.DataType;

public class Context {
	public int dataType;
	public String sender;
	public String source;
	public String destin;
	public String domUserName;
	public String desUserName;
	public String password;
	public String URL;
	public String extendMessage;
	public byte[] byteMessage;
	public int messageSize;
	public int speed;
	public int offset1;
	public int offset2;
	public boolean answerFlag = false;
	public boolean init = false;
	
	public void reset(){
		dataType = 0;
		sender = null;
		source = null;
		destin = null;
		domUserName = null;
		desUserName = null;
		password = null;
		URL = null;
		extendMessage = null;
		speed = 0;
		offset1 = 0;
		offset2 = 0;
	}
}
