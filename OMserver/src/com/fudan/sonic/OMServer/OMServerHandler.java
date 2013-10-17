package com.fudan.sonic.OMServer;

import org.apache.mina.core.service.IoHandlerAdapter;
import java.io.FileOutputStream;
import org.apache.mina.core.session.IoSession;
import com.fudan.sonic.fileCode.BaseMessage;
import com.fudan.sonic.fileCode.NewFile;
import com.fudan.sonic.DataType.Context;
import com.fudan.sonic.DataType.DataType;

public class OMServerHandler extends IoHandlerAdapter{
	
	public void sessionCreated(IoSession session) throws Exception {
		System.out.println("Session " + session.getRemoteAddress().toString() + " is created!");
		super.sessionCreated(session);
	}
	public void sessionOpened(IoSession session) throws Exception {
		System.out.println("Session " + session.getRemoteAddress().toString() + " is created!");
		super.sessionOpened(session);
	}
	public void messageRecived(IoSession session, Object message) throws Exception {
		super.messageReceived(session, message);
		BaseMessage baseMessage = (BaseMessage) message;
		Context context = (Context) session.getAttribute("CONTEXT");
		//****************below test************************
		int dataType = baseMessage.getDataType();
		switch (dataType) {
		case DataType.LOG_IN:
			System.out.println(context.desUserName + "   " + context.desUserName);
			System.out.println("LOG IN SUCCESSFUL!");
			System.out.println();
		default:
			System.out.println("Beyond the decode ability!");
			break;
		}
		//****************above test************************
	}
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		
		super.exceptionCaught(session, cause);
	}
}
