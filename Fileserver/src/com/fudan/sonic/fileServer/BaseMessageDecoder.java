package com.fudan.sonic.fileServer;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.AttributeKey;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;

public class BaseMessageDecoder implements MessageDecoder {
private AttributeKey CONTEXT = new AttributeKey(getClass(), "context");
	
    //is able to decode ?
	public MessageDecoderResult decodable(IoSession session, IoBuffer in){
		Context context = (Context) session.getAttribute(CONTEXT);
		if (context == null) {
			context = new Context();
			context.dataType = in.getInt();
			if (context.dataType == DataType.UPLOAD_FILE) {
				context.strLength = in.getInt();
				context.byteStr = new byte[context.strLength];
				context.fileSize = in.getInt();
				context.offset1 = in.getInt();
				context.offset2 = in.getInt();
				context.byteFile = new byte[context.fileSize];
				session.setAttribute(CONTEXT, context);
				return MessageDecoderResult.OK;
			} 
			else {
				return MessageDecoderResult.NOT_OK;
			}
		}
		else {
			if (context.dataType == DataType.UPLOAD_FILE) {
				return MessageDecoderResult.OK;
			}
			else {
				return MessageDecoderResult.NOT_OK;
			}
		}
	}
	
	// decode
	public MessageDecoderResult decode(IoSession session, IoBuffer in, ProtocolDecoderOutput outPut) throws Exception {
		// decode .. a data packet
		System.out.println("Start to decode...");
		Context context = (Context) session.getAttribute(CONTEXT);
		if (!context.init) {
			context.init = true;
			in.getInt(); //DataType
			in.getInt(); //FileSize
			in.getInt(); //NameLength
			in.getInt(); //offset1
			in.getInt(); //offset2
		}
		byte[] byteFile = context.byteFile;
		int count = context.count;
		while (in.hasRemaining()) {
			byte b = in.get();
			if (!context.isReadName) {
				context.byteStr[count] = b;
				if (count == context.strLength-1) {
					context.fileName = new String(context.byteStr, DataType.charset);
					System.out.println("FileName: "+context.fileName);
					count = -1;
					context.isReadName = true;
				}
			}
			if (context.isReadName && count != -1) {
				byteFile[count] = b;
			}
			count++;
		}
		context.count = count;
		System.out.println("Count: " + count);
		System.out.println("FileSize: " + context.fileSize);
		session.setAttribute(CONTEXT, context);
		if (context.count == context.fileSize) {
				BaseMessage baseMessage = new BaseMessage();
				baseMessage.setDataType(context.dataType);
				NewFile newFile = new NewFile();
				newFile.setFileName(context.fileName);
				newFile.setFileSize(context.fileSize);
				newFile.setOffset1(context.offset1);
				newFile.setOffset2(context.offset2);
				newFile.setFileContent(context.byteFile);
				baseMessage.setData(newFile);
				outPut.write(baseMessage);
				context.reset();
		}
		return MessageDecoderResult.OK;
	}
	
	public void finishDecode(IoSession session, ProtocolDecoderOutput outPut) throws Exception {
		//do some what
		System.out.println("Decode end::::::::::::::::::::::::::::");
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
