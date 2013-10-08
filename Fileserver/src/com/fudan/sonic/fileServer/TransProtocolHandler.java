package com.fudan.sonic.fileServer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.logging.MdcInjectionFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fudan.sonic.dataType.DataType;
import com.fudan.sonic.fileCode.BaseMessage;
import com.fudan.sonic.fileCode.FileHelper;
import com.fudan.sonic.fileCode.NewFile;


public class TransProtocolHandler extends IoHandlerAdapter {
	
    @Override
    public void exceptionCaught(IoSession session, Throwable cause) {
        System.out.println("Warning!!!");
        // Close connection when unexpected exception is caught.
        session.close(true);
    }
  
    @Override  
    public void sessionOpened(IoSession session) throws Exception {  
        System.out.println("Transmision Start!");
        BaseMessage baseMessage = new BaseMessage();
        baseMessage.setDataType(DataType.UPLOAD_FILE);
        NewFile ourFile = new NewFile();
        File file = new File("//home//zhangxu//下载//test.mp3");
        ourFile.setFileName(file.getName());
        ourFile.setFileSize((int) file.length());
        //set file offset need to be done****************************************
        ourFile.setOffset1(1);
        ourFile.setOffset2(1);
        //************************************************************
        try {
        	FileHelper helper = new FileHelper();
        	ourFile.setFileContent(helper.getContent(file));
        } catch (Exception e) {
        	e.printStackTrace();
        }
        baseMessage.setData(ourFile);
        session.write(baseMessage);  
    } 
  
    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
    	super.messageReceived(session, message);    	
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
    	super.sessionClosed(session);
    }

}