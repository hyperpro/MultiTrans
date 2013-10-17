package com.fudan.sonic.fileCode;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

import javax.xml.crypto.Data;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.demux.MessageEncoder;

import com.fudan.sonic.DataType.DataType;

public class BaseMessageEncoder implements MessageEncoder<BaseMessage>{
	
	
	@Override
	public void encode(IoSession session, BaseMessage message,
			ProtocolEncoderOutput outPut) throws Exception {
		IoBuffer buffer = IoBuffer.allocate(8);
		buffer.setAutoExpand(true);
		buffer.putInt(message.getDataType());
		NewFile file = (NewFile) message.getData();
		buffer.putInt(file.getMessageSize());
		buffer.put(file.getMessageContent());
		buffer.flip();
		outPut.write(buffer);
		System.out.println("Server Code Complete!");
	}

}
