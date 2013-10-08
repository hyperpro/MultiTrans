package com.fudan.sonic.OMServer;

import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class OMServer {
	
	public static final int PORT = 1235; 
	public void bindServer() throws Exception{
		IoAcceptor acceptor = new NioSocketAcceptor();
		acceptor.getSessionConfig().setReadBufferSize(1024);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(
				new MathProtocolCodecFactory()));
		System.out.println("Loggin ON!");
		acceptor.setHandler(new OMServerHandler());
		acceptor.bind(new InetSocketAddress(PORT));
	}
	
	public static void main(String[] args) {
		OMServer server = new OMServer();
		try {
			server.bindServer();
			System.out.println("Listening on port "+PORT);
			
		} catch (Exception e) {
			
		}
	}
}
