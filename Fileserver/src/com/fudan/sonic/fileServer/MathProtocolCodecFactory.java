package com.fudan.sonic.fileServer;
import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;

public class MathProtocolCodecFactory extends DemuxingProtocolCodecFactory{
	public MathProtocolCodecFactory(boolean server){
		if (server){
			super.addMessageDecoder(BaseMessageDecoder.class);
		}
		else{
			super.addMessageEncoder(BaseMessage.class, BaseMessageEncoder.class);
		}
	}
}
