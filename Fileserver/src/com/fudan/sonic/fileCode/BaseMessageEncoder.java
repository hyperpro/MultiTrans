package com.fudan.sonic.fileCode;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.demux.MessageEncoder;

import com.fudan.sonic.dataType.DataType;
import com.fudan.sonic.fileCode.BaseMessage;
import com.fudan.sonic.fileCode.NewFile;



public class BaseMessageEncoder implements MessageEncoder<BaseMessage> {
	
	public void encode(IoSession session, BaseMessage message, ProtocolEncoderOutput outPut) throws Exception{
		
		IoBuffer buffer = IoBuffer.allocate(16);
		buffer.setAutoExpand(true);
		NewFile ourFile = (NewFile) message.getData();
		byte[] byteStr = ourFile.getFileName().getBytes(DataType.charset);
		buffer.putInt(message.getDataType());
		buffer.putInt(byteStr.length);
		buffer.putInt(ourFile.getFileSize());
		buffer.putInt(ourFile.getOffset1());
		buffer.putInt(ourFile.getOffset2());
		buffer.put(byteStr);
		buffer.put(ourFile.getFileContent());
		buffer.flip();
		outPut.write(buffer);
		System.out.println("Encode completed!");
	}

}
