package com.fudan.sonic.DataType;

import java.nio.charset.Charset;

public class DataType {
	public final static Charset charset = Charset.forName("UTF-8");
	public final static int UPLOAD_FILE  = 1;
	public final static int DOWNLOAD_FILE = 2;
	public final static int LOG_IN = 3;
	public final static int LOG_OUT = 4;
	public final static int ASK_HELP = 5;
	public final static int ANSWER_HELP = 6;
	public final static int CORD_HELP = 7;
	public final static int CORD_END = 8;
}
