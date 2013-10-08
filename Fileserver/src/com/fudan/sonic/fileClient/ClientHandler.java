package com.fudan.sonic.fileClient;

import java.io.FileOutputStream;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import com.fudan.sonic.fileCode.BaseMessage;
import com.fudan.sonic.fileCode.NewFile;



public class ClientHandler extends IoHandlerAdapter{

	public void sessionCreated(IoSession session) throws Exception {
		System.out.println("Session created!");
		super.sessionCreated(session);
	}
	public void sessionOpened(IoSession session) throws Exception {
		System.out.println("Session opend!");
		super.sessionOpened(session);
	}
	public void messageReceived(IoSession session, Object message) throws Exception {
		super.messageReceived(session, message);
		System.out.println("Get Data!");
		BaseMessage baseMessage = (BaseMessage) message;
		NewFile ourFile = (NewFile) baseMessage.getData();
		System.out.println("Our File Name is: "+ ourFile.getFileName());
		FileOutputStream os = new FileOutputStream("//home//zhangxu//音乐//"+ourFile.getFileName());
		os.write(ourFile.getFileContent());
		os.close();
	}
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		super.exceptionCaught(session, cause);
		
	}

}
