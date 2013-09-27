package com.fudan.sonic.fileServer;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.logging.MdcInjectionFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransProtocolHandler extends IoHandlerAdapter {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Set<IoSession> sessions = Collections
            .synchronizedSet(new HashSet<IoSession>());

    private final Set<String> users = Collections
            .synchronizedSet(new HashSet<String>());
    
    @Override
    public void exceptionCaught(IoSession session, Throwable cause) {
        logger.warn("Unexpected exception.", cause);
        // Close connection when unexpected exception is caught.
        session.close(true);
    }
    
    @Override  
    protected void processStreamIo(IoSession session, InputStream in,  
            OutputStream out) {  
            //客户端发送文件  
            File sendFile = new File("F:\\ttt.pdf");  
            FileInputStream fis = null;  
            try {  
                fis = new FileInputStream(sendFile);  
                  
            } catch (FileNotFoundException e) {  
                e.printStackTrace();  
            }  
            //放入线程让其执行  
            //客户端一般都用一个线程实现即可 不用线程池  
            new IoStreamThreadWork(fis,out).start();  
            return;  
    }  

    @Override
    public void messageReceived(IoSession session, Object message) {
        Logger log = LoggerFactory.getLogger(TransProtocolHandler.class);
        log.info("received: message");
        String theMessage = (String) message;
        String[] result = theMessage.split(" ", 2);
        String theCommand = result[0];

        try {

            ChatCommand command = ChatCommand.valueOf(theCommand);
            String user = (String) session.getAttribute("user");
            String url;
            int BlockNumber1, BlockNumber2;

            switch (command.toInt()) {

            case ChatCommand.QUIT:
                session.write("QUIT OK");
                session.close(true);
                break;
                
            case ChatCommand.LOGIN:

                if (user != null) {
                    session.write("LOGIN ERROR user " + user
                            + " already logged in.");
                    return;
                }

                if (result.length == 2) {
                    user = result[1];
                } else {
                    session.write("LOGIN ERROR invalid login command.");
                    return;
                }

                // check if the username is already used
                if (users.contains(user)) {
                    session.write("LOGIN ERROR the name " + user
                            + " is already used.");
                    return;
                }

                sessions.add(session);
                session.setAttribute("user", user);
                MdcInjectionFilter.setProperty(session, "user", user);

                // Allow all users
                users.add(user);
                session.write("LOGIN OK");
                break;
            
            case ChatCommand.GETFILE:
            	if (result.length == 2) {
            		url = result[1];
            		session.write("GETURL");
            	} else {
            		session.write("NO URL REQUIRE");
            		return;
            	}
            	break;
            	
            case ChatCommand.REQUEST:
            	if (result.length == 2) {
            		String[] TempBlock = result[1].split("-", 2);
            		BlockNumber1 = Integer.parseInt(TempBlock[0]);
            		BlockNumber2 = Integer.parseInt(TempBlock[2]);
            		
            	} else {
            		session.write("REQUEST FAILED");
            	}
            	break;
            	
            case ChatCommand.ENDFILE:
            	break;
            
            default:
                logger.info("Unhandled command: " + command);
                break;
            }

        } catch (IllegalArgumentException e) {
            logger.debug("Illegal argument", e);
        }
    }

    public void broadcast(String message) {
        synchronized (sessions) {
            for (IoSession session : sessions) {
                if (session.isConnected()) {
                    session.write("BROADCAST OK " + message);
                }
            }
        }
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        String user = (String) session.getAttribute("user");
        users.remove(user);
        sessions.remove(session);
        broadcast("The user " + user + " has left the chat session.");
    }

    public boolean isChatUser(String name) {
        return users.contains(name);
    }

    public int getNumberOfUsers() {
        return users.size();
    }

    public void kick(String name) {
        synchronized (sessions) {
            for (IoSession session : sessions) {
                if (name.equals(session.getAttribute("user"))) {
                    session.close(true);
                    break;
                }
            }
        }
    }
}