package com.aerodynamic.design.domain.fileprocess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReadDataFile {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	public static List<String> readFile(String path){
		String parent = "E:/17-06-07/EXE";
		File file = new File(parent+"/"+path);
		return readFile(file);
	}
	
	public static List<String> readFile(File file){
		List<String> result = new ArrayList<String>();
		
		BufferedReader in = null;
		long count = 0;
		
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line = null;
			while((line = in.readLine())!=null){
				result.add(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	public static String[] spliteRow(String row,int count){
		String[] result = new String[count];
		if(row!=null){
			String[] temp = row.split(" ");
			if(temp!=null){
				if(temp.length==count){
					result = temp;
				}else if(temp.length>count){
					for(int i=0;i<count;i++){
						result[i] = temp[i];
					}
				}else if(temp.length<count){
					for(int i=0;i<temp.length;i++){
						result[i] = temp[i];
					}
					/*
					for(int i=temp.length-1;i<count;i++){
						result[i] = "0";
					}
					*/
				}
			}
		}
		return result;
	}
	
	public static String[] spliteRow(String row,String spliteStr,int count){
		String[] result = new String[count];
		if(row!=null){
			String[] temp = row.split(spliteStr);
			if(temp!=null){
				if(temp.length==count){
					result = temp;
				}else if(temp.length>count){
					for(int i=0;i<count;i++){
						result[i] = temp[i];
					}
				}else if(temp.length<count){
					for(int i=0;i<temp.length;i++){
						result[i] = temp[i];
					}
				}
			}
		}
		return result;
	}
	
	public static List<String> spliteRowByWidth(String row,int colWidth){
		List<String> list = new ArrayList<String>();
		int pos = 0;
		while(pos<row.length()){
			String item = "";
			if(pos+colWidth>row.length()){
				item = row.substring(pos);
			}else{
				item = row.substring(pos, pos+colWidth);
			}
			list.add(item);
			pos += colWidth;
		}
		return list;
	}
	
	public static List<String> spliteRowByWidth(String row,int colWidth,int count){
		List<String> list = new ArrayList<String>();
		int pos = 0;
		while(pos<row.length()){
			String item = "";
			if(pos+colWidth>row.length()){
				item = row.substring(pos);
			}else{
				item = row.substring(pos, pos+colWidth);
			}
			list.add(item.trim());
			pos += colWidth;
		}
		
		if(list.size()<count){
			for(int i=list.size();i<count;i++){
				list.add("");
			}
		}
		return list;
	}
	
	public static String[] spliteRow(String row,int colWidth,int count){
		/*
		String[] result = new String[count];
		if(row!=null && row.length()>=colWidth*count){
			int pos = 0;
			for(int i=0;i<count;i++){
				result[i] = row.substring(pos, pos+colWidth);
				pos += colWidth;
				if(result[i]!=null){
					result[i] = result[i].trim();
				}
			}
		}
		*/
		int[] colWidths = new int[count];
		for(int i=0;i<count;i++){
			colWidths[i] = colWidth;
		}
		return spliteRow(row,colWidths);
	}
	
	public static String[] spliteRow(String row,int[] colWidths){
		int count = colWidths.length;
		String[] result = new String[count];
		int sumWidth = 0;
		for(int width:colWidths){
			sumWidth += width;
		}
		if(row!=null && row.length()>=sumWidth){
			int pos = 0;
			for(int i=0;i<count;i++){
				result[i] = row.substring(pos, pos+colWidths[i]);
				pos += colWidths[i];
				if(result[i]!=null){
					result[i] = result[i].trim();
				}
			}
		}
		return result;
	}
	
	public static String[] spliteRow(String row){
		List<String> list = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<row.length();i++){
			if(row.charAt(i)!=' '){
				sb.append(row.charAt(i));
			}else{
				if(row.charAt(i)==' ' && row.charAt(i-1)!=' '){
					sb.append(' ');
				}
			}
		}
		//logger.info(sb.toString());
		
		return sb.toString().split(" ");
	}
	
	public static void addLineToGrid(String line,String spliteStr,int istage,List<List<String>> grid){
		List<String> rowList = null;
		String[] arrayString = null;
		arrayString = spliteRow(line,spliteStr,istage);
		
		rowList = new ArrayList<String>();
		for(String colData:arrayString){
			rowList.add(colData!=null?colData.trim():colData);
		}
		grid.add(rowList);
	}
	
	public static void readFileDataByColWidths(List<List<String>> list,File file,int[] colWidths){
		List<String> rows = readFile(file);
		for(String row:rows){
			List<String> rowList = new ArrayList<String>();
			String[] arrayString = spliteRow(row, colWidths);
			for(String col:arrayString){
				if(col!=null && !"".equals(col)){
					rowList.add(col);
				}
			}
			list.add(rowList);
		}
	}
	
	public static void readFileData(List<List<String>> list,File file){
		List<String> rows = readFile(file);
		for(String row:rows){
			List<String> rowList = new ArrayList<String>();
			String[] arrayString = spliteRow(row);
			for(String col:arrayString){
				if(col!=null && !"".equals(col)){
					rowList.add(col);
				}
			}
			list.add(rowList);
		}
	}
	
	public static List<List<String>> readFileDataByColWidths(File file,int[] colWidths){
		List<List<String>> result = new ArrayList<List<String>>();
		readFileDataByColWidths(result,file,colWidths);
		return result;
	}
	
	public static List<List<String>> readFileDataByColWidths(String fileName,int[] colWidths){
		File file = new File(fileName);
		return readFileDataByColWidths(file,colWidths);
	}
}
