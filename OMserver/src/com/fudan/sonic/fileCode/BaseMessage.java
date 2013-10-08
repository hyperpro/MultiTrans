package com.fudan.sonic.fileCode;

public class BaseMessage {

	private int dataType;
	private Object data;
	
	public BaseMessage(int dataType, Object data) {
		this.dataType = dataType;
		this.data = data;
	}
	public BaseMessage() {
		
	}
	public int getDataType() {
		return dataType;
	}
	public void setDataType(int dataType) {
		this.dataType = dataType;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data){
		this.data = data;
	}
		
}
