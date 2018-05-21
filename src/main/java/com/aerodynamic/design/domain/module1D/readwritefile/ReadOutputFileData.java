package com.aerodynamic.design.domain.module1D.readwritefile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aerodynamic.design.domain.fileprocess.GridDataUtil;
import com.aerodynamic.design.domain.fileprocess.ReadDataFile;

public class ReadOutputFileData extends ReadDataFile{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	public static String path = "E:/17-06-07/EXE";
	
	public static void main(String[] args) {

		List<List<String>> gridData = readAeroData();
		GridDataUtil.printList(gridData);
		//gridData = GridDataUtil.trim(gridData,0,6);
		gridData = GridDataUtil.transform(gridData);
		//gridData = GridDataUtil.trim(gridData,1,16);
		GridDataUtil.printList(gridData);

		/*
		List<List<String>> gridData = readFlowPathData();
		GridDataUtil.printList(gridData);
		*/
		/*
		List<List<String>> gridData = ReadOutputFileData.readPerfData();
		GridDataUtil.printList(gridData);
		*/
	}
	
	
	
	public static List<List<String>> readAeroData(){
		List<List<String>> result = new ArrayList<List<String>>();
		
		//String fileName = path+"/1D/aero.dat";
		String fileName = path+"/1D/aero.dat";
		File file = new File(fileName);
		
		List<String> rows = ReadInputFileData.readFile(file);
		for(String row:rows){
			String[] arrayString = row.split(" ");
			List<String> rowList = new ArrayList<String>();
			for(String col:arrayString){
				if(col!=null && !"".equals(col)){
					rowList.add(col);
				}
			}
			result.add(rowList);
		}
		
		return result;
	}
	
	public static List<List<String>> readAeroData(File file){
		List<List<String>> result = new ArrayList<List<String>>();
		
		List<String> rows = ReadInputFileData.readFile(file);
		for(String row:rows){
			String[] arrayString = row.split(" ");
			List<String> rowList = new ArrayList<String>();
			for(String col:arrayString){
				if(col!=null && !"".equals(col)){
					rowList.add(col);
				}
			}
			result.add(rowList);
		}
		
		return result;
	}
	
	public static List<List<String>> readFoilData(){
		List<List<String>> result = new ArrayList<List<String>>();
		
		//String fileName = path+"/1D/foil.dat";
		String fileName = path+"/1D/foil.dat";
		File file = new File(fileName);
		
		List<String> rows = ReadInputFileData.readFile(file);
		for(String row:rows){
			String[] arrayString = row.split(" ");
			List<String> rowList = new ArrayList<String>();
			for(String col:arrayString){
				if(col!=null && !"".equals(col)){
					rowList.add(col);
				}
			}
			result.add(rowList);
		}
		
		return result;
	}
	
	public static List<List<String>> readFoilData(File file){
		List<List<String>> result = new ArrayList<List<String>>();
		
		List<String> rows = ReadInputFileData.readFile(file);
		for(String row:rows){
			String[] arrayString = row.split(" ");
			List<String> rowList = new ArrayList<String>();
			for(String col:arrayString){
				if(col!=null && !"".equals(col)){
					rowList.add(col);
				}
			}
			result.add(rowList);
		}
		
		return result;
	}
	
	public static List<List<String>> readGrfData(){
		List<List<String>> result = new ArrayList<List<String>>();
		
		String fileName = path+"/1D/grf.dat";
		String path = ReadInputFileData.class.getResource("/").getPath();
		File file = new File(fileName);
		
		List<String> rows = ReadInputFileData.readFile(file);
		for(String row:rows){
			List<String> rowList = new ArrayList<String>();
			String[] arrayString = row.split(" ");
			for(String col:arrayString){
				if(col!=null && !"".equals(col)){
					rowList.add(col);
				}
			}
			result.add(rowList);
		}
		
		return result;
	}
	
	public static List<List<String>> readGrfData(File file){
		List<List<String>> result = new ArrayList<List<String>>();
		
		List<String> rows = ReadInputFileData.readFile(file);
		for(String row:rows){
			List<String> rowList = new ArrayList<String>();
			String[] arrayString = row.split(" ");
			for(String col:arrayString){
				if(col!=null && !"".equals(col)){
					rowList.add(col);
				}
			}
			result.add(rowList);
		}
		
		return result;
	}
	
	public static List<List<String>> readPerf2Data(){
		List<List<String>> result = new ArrayList<List<String>>();
		
		//String fileName = path+"/1D/perf2.dat";
		String fileName = path+"/1D/perf2.dat";
		File file = new File(fileName);
		
		List<String> rows = ReadInputFileData.readFile(file);
		for(String row:rows){
			List<String> rowList = new ArrayList<String>();
			String[] arrayString = row.split(" ");
			for(String col:arrayString){
				if(col!=null && !"".equals(col)){
					rowList.add(col);
				}
			}
			result.add(rowList);
		}
		
		return result;
	}
	
	public static List<List<String>> readPerf2Data(File file){
		List<List<String>> result = new ArrayList<List<String>>();
		
		List<String> rows = ReadInputFileData.readFile(file);
		for(String row:rows){
			List<String> rowList = new ArrayList<String>();
			String[] arrayString = row.split(" ");
			for(String col:arrayString){
				if(col!=null && !"".equals(col)){
					rowList.add(col);
				}
			}
			result.add(rowList);
		}
		
		return result;
	}
	
	public static List<List<String>> readSurgData(String workPath){
		return readFileDataByColWidths(workPath+"/1D/surg.dat",new int[]{12,10});
	}
	
	public static List<List<String>> readOperData(String workPath){
		return readFileDataByColWidths(workPath+"/1D/oper.dat",new int[]{11,10});
	}
	
	public static List<List<String>> readPerfData(String workPath){
		List<List<String>> result = new ArrayList<List<String>>();
		File directory = new File(workPath+"/1D");
		if(directory.exists() && directory.isDirectory()){
			File[] files = directory.listFiles();
			for(File file:files){
				if(file.isFile() && file.getName().startsWith("perf")){
					readFileData(result,file);
				}
			}
		}
		return result;
	}
	
	public static List<List<String>> readPerfData(File file){
		List<List<String>> result = new ArrayList<List<String>>();
		readFileData(result,file);

		return result;
	}
	
	public static List<List<String>> readPathData(){
		List<List<String>> result = new ArrayList<List<String>>();
		
		String fileName = path+"/1D/path";
		File file = new File(fileName);
		
		List<String> rows = ReadInputFileData.readFile(file);

		int lineLength = Integer.parseInt(rows.get(0).trim());
		List<String> row = null;
		int rowCount = 0;
		int pos = 0;
		
		List<List<String>> lists = new ArrayList<List<String>>();
		for(int line=0;line<3;line++){
			List<String> list = new ArrayList<String>();
			for(int i=0;i<lineLength;i++){
				if(row==null || pos==row.size()){
					rowCount++;
					row = GridDataUtil.splitByBlank(rows.get(rowCount));
					pos=0;
				}
				list.add(row.get(pos));
				pos++;
			}
			lists.add(list);
		}
		
		return result;
	}
	
	public static List<List<List<String>>> readPathData(File file){
		List<List<List<String>>> result = new ArrayList<List<List<String>>>();

		List<String> rows = ReadInputFileData.readFile(file);

		int lineLength = Integer.parseInt(rows.get(0).trim());
		List<String> row = null;
		int rowCount = 0;
		int pos = 0;
		
		List<List<String>> lists = new ArrayList<List<String>>();
		for(int line=0;line<3;line++){
			List<String> list = new ArrayList<String>();
			for(int i=0;i<lineLength;i++){
				if(row==null || pos==row.size()){
					rowCount++;
					row = GridDataUtil.splitByBlank(rows.get(rowCount));
					pos=0;
				}
				list.add(row.get(pos));
				pos++;
			}
			lists.add(list);
		}
		
		List<List<String>> line1 = new ArrayList<List<String>>();
		List<List<String>> line2 = new ArrayList<List<String>>();
		for(int i=0;i<lineLength;i++){
			List<String> point = new ArrayList<String>();
			String x = lists.get(0).get(i);
			String y = lists.get(1).get(i);
			
			point.add(x); 
			point.add(y);
			line1.add(point);
			
			point = new ArrayList<String>();
			y = lists.get(2).get(i);
			point.add(x); 
			point.add(y);
			line2.add(point);
		}
		result.add(line1);
		result.add(line2);
		
		for(int i=0;i<line1.size();i++){
			List<String> point1 = line1.get(i);
			List<String> point2 = line2.get(i);
			
			List<List<String>> line = new ArrayList<List<String>>();
			line.add(point1);
			line.add(point2);
			
			result.add(line);
		}
		
		return result;
	}
}
