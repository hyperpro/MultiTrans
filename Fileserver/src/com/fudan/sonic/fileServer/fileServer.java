package com.fudan.sonic.fileServer;
import java.net.InetSocketAddress;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.filter.logging.MdcInjectionFilter;
import org.apache.mina.filter.ssl.SslFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;


public class fileServer {

	public static final int PORT = 1234;
	
	public static void main(String[] args) throws Exception{
		NioSocketAcceptor acceptor = new NioSocketAcceptor();
		DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
		MdcInjectionFilter mdcInjectionFilter = new MdcInjectionFilter();
		chain.addLast("mdc", mdcInjectionFilter);	 	 
		chain.addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory()));
		addLogger(chain);	 
		// Bind
		acceptor.setHandler(new TransProtocolHandler());
		acceptor.bind(new InetSocketAddress(PORT));
		System.out.println("Listening on port " + PORT);
	}
	
	private static void addLogger(DefaultIoFilterChainBuilder chain)
			throws Exception {
		chain.addLast("logger", new LoggingFilter());
		System.out.println("Logging ON");
	}
}
