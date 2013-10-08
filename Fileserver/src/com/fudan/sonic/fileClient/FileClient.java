package com.fudan.sonic.fileClient;

import java.net.InetSocketAddress;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.fudan.sonic.fileCode.MathProtocolCodecFactory;


public class FileClient {

	public IoSession creatClient() {
		IoConnector connector = new NioSocketConnector();
		connector.setConnectTimeoutMillis(30000);
		connector.getFilterChain().addLast("codec", 
				new ProtocolCodecFilter(new MathProtocolCodecFactory()));
		connector.setHandler(new ClientHandler());
		ConnectFuture future = connector.connect(new InetSocketAddress("127.0.0.1", 1234));
		future.awaitUninterruptibly();
		IoSession session = null;
		try {
			session = future.getSession();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return session;
	}
	public static void main(String[] args) {
		FileClient fileClient = new FileClient();
		fileClient.creatClient();
	}
}
