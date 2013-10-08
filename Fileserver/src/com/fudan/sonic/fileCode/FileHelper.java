package com.fudan.sonic.fileCode;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


public class FileHelper {
	
	//according to the file path
	//.................
	//according to the file class
	public byte[] getContent(File file) throws IOException{
		long fileSize = file.length();
		if (fileSize > Integer.MAX_VALUE) {
			System.out.println("File is tooooo big...");
			return null;
		}
		FileInputStream in = new FileInputStream(file);
		byte[] buffer = new byte[(int) fileSize];
		int offset = 0;
		int numRead = 0;
		while (offset < buffer.length && (numRead = in.read(buffer, offset, buffer.length-offset)) >= 0)
		{
			offset += numRead;
		}
		//check for error
		if (offset != buffer.length) {
			throw new IOException("Could Not Read File "+file.getName());
		}
		in.close();
		return buffer;
	}
	
}
