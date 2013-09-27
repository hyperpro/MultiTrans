package com.fudan.sonic.fileServer;


public class ChatCommand {
    public static final int LOGIN = 0;
    
    public static final int QUIT = 1;

    public static final int GETFILE = 2;
    
    public static final int REQUEST = 3;
    
    public static final int ENDFILE = 4;

    private final int num;

    private ChatCommand(int num) {
        this.num = num;
    }
    
    public int toInt() {
        return num;
    }

    public static ChatCommand valueOf(String s) {
        s = s.toUpperCase();
        if ("LOGIN".equals(s)) {
            return new ChatCommand(LOGIN);
        }
        if ("QUIT".equals(s)) {
            return new ChatCommand(QUIT);
        }
        if ("GETFILE".equals(s)) {
            return new ChatCommand(GETFILE);
        }
        if ("REQUEST".equals(s)) {
        	return new ChatCommand(REQUEST);
        }
        if ("REQUEST".equals(s)) {
        	return new ChatCommand(ENDFILE);
        }

        throw new IllegalArgumentException("Unrecognized command: " + s);
    }
}
