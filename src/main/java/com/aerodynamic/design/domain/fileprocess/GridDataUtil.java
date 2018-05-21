package com.aerodynamic.design.domain.fileprocess;

import java.util.ArrayList;
import java.util.List;

public class GridDataUtil {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

	public static List<List<String>> transform(List<List<String>> grid){
		
		List<List<String>> result = new ArrayList<List<String>>();
		
		//ReadInputFileGridData.printList(grid);
		if(grid!=null && grid.size()>0){
			int maxSize = grid.get(0).size();
			for(List<String> row:grid){
				if(row.size()>maxSize){
					maxSize = row.size();
				}
			}
			
			for(int i=0;i<maxSize;i++){
				List<String> row = new ArrayList<String>();
				if(row!=null){
					result.add(row);
				}
			}
			
			int count = grid.size();
			for(int i=0;i<count;i++){
				List<String> row = grid.get(i);
				for(int j=0;j<maxSize;j++){
					String value = j<row.size()?String.valueOf(row.get(j)):"";
					result.get(j).add(value);
				}
			}
		}
		
		//System.out.println("--------------------------------");
		
		//ReadInputFileGridData.printList(result);
		
		return result;
	}
	
	public static List<List<String>> trim(List<List<String>> grid,int left,int right,int top,int bottom){
		List<List<String>> result = new ArrayList<List<String>>();
		
		for(int i=top;i<=bottom;i++){
			List<String> row = new ArrayList<String>();
			List<String> temp = grid.get(i);
			for(int j=left;j<=right;j++){
				row.add(temp.get(j));
			}
			result.add(row);
		}
		
		return result;
	}
	
	public static List<List<String>> trim(List<List<String>> grid,int startRow,int endRow){
		List<List<String>> result = new ArrayList<List<String>>();
		
		for(int i=startRow;i<=endRow;i++){
			result.add(grid.get(i));
		}
		
		return result;
	}
	
	public static void printList(List<List<String>> list){
		StringBuilder sb = new StringBuilder();
		for(List<String> row:list){
			for(String str:row){
				sb.append(str).append("\t");
			}
			sb.append("\r\n");
		}
		
		System.out.println(sb.toString());
	}
	
	public static List<String> splitByBlank(String line){
		List<String> result = new ArrayList<String>();
		
		String[] datas = line.split(" ");
		for(int i=0;i<datas.length;i++){
			if(datas[i]!=null && !"".equals(datas[i].trim())){
				result.add(datas[i]);
			}
		}
		return result;
	}
}