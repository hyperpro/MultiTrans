package com.fudan.sonic.fileCode;

import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;

public class MathProtocolCodecFactory extends DemuxingProtocolCodecFactory{
		public MathProtocolCodecFactory(){
			addMessageDecoder(BaseMessageDecoder.class);
			addMessageEncoder(BaseMessage.class, BaseMessageEncoder.class);
		}
}
