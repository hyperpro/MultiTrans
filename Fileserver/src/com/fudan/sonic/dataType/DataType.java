package com.fudan.sonic.dataType;
import java.nio.charset.Charset;

public class DataType {

	public final static Charset charset = Charset.forName("UTF-8");
	public final static int UPLOAD_FILE  = 1;
	public final static int DOWNLOAD_FILE = 2;
	public final static int TRANS_INFO = 3;
}
