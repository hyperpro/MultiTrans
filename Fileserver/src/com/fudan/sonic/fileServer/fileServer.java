package com.fudan.sonic.fileServer;

import java.net.InetSocketAddress;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.fudan.sonic.fileCode.MathProtocolCodecFactory;


public class fileServer {

	public static final int PORT = 1234;
	
	public static void main(String[] args) throws Exception{
		NioSocketAcceptor acceptor = new NioSocketAcceptor();
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
		chain.addLast( "codec", new ProtocolCodecFilter(
                new MathProtocolCodecFactory(true)));
		addLogger(chain);
		acceptor.setHandler(new TransProtocolHandler());
		try{
			acceptor.bind(new InetSocketAddress(PORT));
			System.out.println("Listening on port " + PORT);
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	private static void addLogger(DefaultIoFilterChainBuilder chain)
			throws Exception {
		chain.addLast("logger", new LoggingFilter());
		System.out.println("Logging ON");
	}
}
