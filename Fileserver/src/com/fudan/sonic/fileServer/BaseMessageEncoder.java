package com.fudan.sonic.fileServer;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.AttributeKey;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;

import com.fudan.sonic.fileServer.BaseMessage;
import com.fudan.sonic.fileServer.NewFile;

public class BaseMessageEncoder {
	private AttributeKey CONTEXT = new AttributeKey(getClass(), "context");
	
	public MessageDecoderResult decodable(IoSession session, IoBuffer in){
		Context context = (Context) session.getAttribute(CONTEXT);
		if (context == null) {
			context = new Context();
			context.dataType = in.getInt();
			if () {
				
			}
		}
		return MessageDecoderResult.NOT_OK;
	}
	
	private class Context {
		public int dataType;
		public byte[] byteFile;
		public int offset1, offset2;
		public int count;
		public int strLength;
		public boolean isReadName;
		public int fileSize;
		public byte[] byteStr;
		public String fileName;
		public boolean init = false;
		
		public void reset(){
			dataType = 0;
			byteFile = null;
			count = 0;
			offset1 = 0;
			offset2 = 0;
			strLength = 0;
			isReadName = false;
			fileSize = 0;
			byteStr = null;
			fileName = null;
		}
	}

}
