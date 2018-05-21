package com.aerodynamic.design.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;

import com.aerodynamic.design.DesignApplication;

public class StringUtil {
	public static String CHARSET_NAME = "GBK";
	public static String rightPad(String str ,int size){
		return rightPad(str ,size,"");
	}
	
	public static String leftPad(String str ,int size){
		return leftPad(str ,size,"");
	}
	
	public static String rightPad(String str ,int size,String padStr){
		String result = null;
		str = getStringByLength(str,size);
		result = StringUtils.rightPad(str,str.length()+(size-getByteLength(str)),padStr);
		return result;
	}
	
	public static String leftPad(String str ,int size,String padStr){
		String result = null;
		str = getStringByLength(str,size);
		result = StringUtils.leftPad(str,str.length()+(size-getByteLength(str)),padStr);
		return result;
	}
	
	public static String getStringByLength(String str ,int size){
		int strLength = 0;
		if(str==null){
			str = "";
		}
		try {
			strLength = str.getBytes(CHARSET_NAME).length;
			while(strLength>size){
				str = str.substring(0, str.length()-1);
				strLength = str.getBytes(CHARSET_NAME).length;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	public static int getByteLength(String str){
		int length = 0;
		try {
			length = str.getBytes(CHARSET_NAME).length;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return length;
	}
	
	public static void writeStringBufferToStream(StringBuffer buffer,OutputStream os){
		writeStringToStream(buffer.toString(),os);
		buffer.delete(0, buffer.length());
	}
	
	public static void writeStringToStream(String data,OutputStream os){
		byte[] buffer = null;
		try {
			buffer = data.getBytes(CHARSET_NAME);
			os.write(buffer, 0, buffer.length);
		}catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public static void writeStringToFile(String data,File file){
		OutputStream os = null;
		byte[] buffer = null;
		try {
			os = new FileOutputStream(file);
			buffer = data.getBytes(CHARSET_NAME);
			os.write(buffer, 0, buffer.length);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(os!=null){
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static String[] parse(byte[] data,int[] colLengths){
		String[] datas = new String[colLengths.length];
		int length = 0;
		for (int len : colLengths) {
			length += len;
		}
		
		if(data!=null && data.length==length){
			int startPos = 0;
			for (int i = 0; i < colLengths.length; i++) {
				try {
					datas[i] = new String(data, startPos, colLengths[i],StringUtil.CHARSET_NAME).trim();
					startPos += colLengths[i];
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		return datas;
	}
	
	//金额转换
	public static String getChangeValue(double d){
		/*String value = null;
		
		if(d!=0){
			DecimalFormat df1 = new DecimalFormat("0.##");
			
			value = df1.format(d);
			//value = String.valueOf(d);
			if((value.length()-value.indexOf("."))==2){
				value = value + "0";
			}
		}*/
		DecimalFormat df1 = new DecimalFormat("0.00");
		String value = df1.format(d);
		
		return value;
	
	}
	
	public static String[] parse(String line,int[] colLengths){
		byte[] data = null;
		try {
			data = line.getBytes(StringUtil.CHARSET_NAME);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return parse(data,colLengths);
	}
	
	public static void main(String[] args) {
		String data = "王馨䜣";
		File file = new File("d:/test.txt");
		OutputStream os = null;
		byte[] buffer = null;
		try {
			os = new FileOutputStream(file);
			buffer = data.getBytes("GB18030");
			os.write(buffer, 0, buffer.length);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(os!=null){
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
