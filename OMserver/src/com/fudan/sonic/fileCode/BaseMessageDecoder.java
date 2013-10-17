package com.fudan.sonic.fileCode;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.AttributeKey;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;
import com.fudan.sonic.DataType.Context;

import com.fudan.sonic.DataType.DataType;

public class BaseMessageDecoder implements MessageDecoder {

	private AttributeKey CONTEXT = new AttributeKey(getClass(), "context");
	
	@Override
	public MessageDecoderResult decodable(IoSession session, IoBuffer in) {
		// TODO Auto-generated method stub
		Context context = (Context) session.getAttribute(CONTEXT);
		if (context == null) {
			context = new Context();
			context.dataType = in.getInt();
			session.setAttribute(CONTEXT, context);
		}
		if (context.dataType > 10 || context.dataType < 1)
		{
			return MessageDecoderResult.NOT_OK;
		}
		return MessageDecoderResult.OK;
	}

	@Override
	public MessageDecoderResult decode(IoSession session, IoBuffer in,
			ProtocolDecoderOutput outPut) throws Exception {
		// TODO Auto-generated method stub
		System.out.println();
		Context context = (Context) session.getAttribute(CONTEXT);
		if (context.init == false)
		{
			in.getInt();
			context.messageSize = in.getInt();
			context.init = true;
		}
		
		byte[] messageStr = new byte[context.messageSize];
		in.get(messageStr);
		String message = new String(messageStr, DataType.charset);
		String[] messageArray = message.split(" ");
		context.sender = messageArray[0];
		context.source = messageArray[1];
		context.destin = messageArray[2];
		switch (context.dataType) {
		case DataType.LOG_OUT:
		case DataType.LOG_IN:
			context.domUserName = messageArray[3];
			context.password = messageArray[4];
			break;
		case DataType.LOG_INFO:
			if (messageArray[3].equals("success"))
				context.answerFlag = true;
			else
				context.answerFlag = false;
			break;
		case DataType.ASK_HELP:
			context.speed = Integer.parseInt(messageArray[3]);
			context.URL = messageArray[4];
			context.extendMessage = messageArray[5];
			break;
		case DataType.ANSWER_HELP:
			if (messageArray[3].equals("yes"))
				context.answerFlag = true;
			else
				context.answerFlag = false;
			context.speed = Integer.parseInt(messageArray[4]);
			break;
		case DataType.ENSURE_HELP:
			context.offset1 = Integer.parseInt(messageArray[3]);
			context.offset2 = Integer.parseInt(messageArray[4]);
			break;
		case DataType.CORD_HELP:
			context.domUserName = messageArray[3];
			context.offset1 = Integer.parseInt(messageArray[3]);
			context.offset2 = Integer.parseInt(messageArray[4]);
			break;
		case DataType.CORD_END:
			if (messageArray[3].equals("yes")) 
				context.answerFlag = true;
			else
				context.answerFlag = false;
			break;
		default:
			System.out.println();
			break;
		}
		return MessageDecoderResult.OK;
	}

	@Override
	public void finishDecode(IoSession session, ProtocolDecoderOutput outPut)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Decode End::::::::::::::::::::::::::");
		
	}
	
	
}
