package com.fudan.sonic.fileCode;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.demux.MessageEncoder;

import com.fudan.sonic.DataType.DataType;

public class BaseMessageEncoder implements MessageEncoder<BaseMessage>{

	@Override
	public void encode(IoSession session, BaseMessage message,
			ProtocolEncoderOutput outPut) throws Exception {
		// TODO Auto-generated method stub
		IoBuffer buffer = IoBuffer.allocate(8);
		buffer.setAutoExpand(true);
		buffer.putInt(message.getDataType());
		NewFile file = (NewFile) message.getData();
		byte[] byteStr = file.getUserName().getBytes(DataType.charset);
		buffer.putInt(byteStr.length);
		buffer.putInt();
	}

}
