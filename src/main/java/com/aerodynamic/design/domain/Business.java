package com.aerodynamic.design.domain;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.aerodynamic.design.controller.AdminController;
import com.aerodynamic.design.domain.fileprocess.ReadDataFile;
import com.aerodynamic.design.domain.module1D.Module1DBusiness;
import com.aerodynamic.design.domain.module1D.Module1DCodeList;
import com.aerodynamic.design.domain.moduleS2.ModuleS2Business;
import com.aerodynamic.design.domain.moduleS2.ModuleS2CodeList;

public abstract class Business {
	public static Map<String,String> fileMap = new HashMap<String,String>();
	private static Map<String,Object> CONTEXT = new HashMap<String,Object>();
	public static String workPath = "E:/17-06-07/EXE";
	static{
		fileMap.put("input_1d_1", "1D/1d_in1");
		fileMap.put("input_1d_2", "1D/1d_in2");
		fileMap.put("input_s2_1", "s2/s2.in");
	}
	
	private String sessionid = null;
	private String fileid = null;
	public abstract void WriteDataToFile(Map map);
	public abstract Map parse();
	public abstract Map parse(String fileid,List<String> list);
	public List<String> readFile(File file){
		return ReadDataFile.readFile(file);
	}
	public List<String> readFile(){
		String path = AdminController.getWorkPath(this.getSessionid());
		File file = getModuleFile(path,fileMap.get(this.getFileid()));
		return ReadDataFile.readFile(file);
	}
	public List<String> readFile(String fileid){
		String path = AdminController.getWorkPath(this.getSessionid());
		File file = getModuleFile(path,fileid);
		return ReadDataFile.readFile(file);
	}
	public static Business getBusiness(String fileid){
		Business result = null;
		if("input_1d_1".equals(fileid)){
			result = new Module1DBusiness();
		}else if("input_1d_2".equals(fileid)){
			result = new Module1DBusiness();
		}else if("input_s2_1".equals(fileid)){
			result = new ModuleS2Business();
		}
		result.setFileid(fileid);
		return result;
	}
	public static Business getBusiness(String fileid,String sessionid){
		Business result = getBusiness(fileid);
		result.setSessionid(sessionid);
		return result;
	}
	public static Map<String,CodeList> getCodeList(String module){
		Map<String,CodeList> map = null;
		if("1D".equalsIgnoreCase(module)){
			map = Module1DCodeList.codeListMap;
		}else if("S2".equalsIgnoreCase(module)){
			map = ModuleS2CodeList.codeListMap;
		}else{
			map = new HashMap();
		}
    	return map; 
	}
	public static File getModuleFile(String workPath,String fileid){
		String fileName = fileMap.get(fileid);
		if(fileName==null || "".equals(fileName)){
			fileName = fileid;
		}
		File file = new File(workPath+"/"+fileName);
		return file;
	}
	public static Map getMap(Map input){
		Map result = new HashMap();

		Set keys = input.keySet();
		for(Object key: keys){
			Object value = input.get(key);
			if(value instanceof Map){
				Map temp = (Map)value;
				if(temp.get("label")!=null && temp.get("value")!=null){
					result.put(temp.get("label"), temp.get("value"));
				}else{
					result.put(key, getMap(temp));
				}
			}else{
				result.put(key, value);
			}
		}

		return result;
	}
	public String getSessionid() {
		return sessionid;
	}
	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}
	public String getFileid() {
		return fileid;
	}
	public void setFileid(String fileid) {
		this.fileid = fileid;
	}
	
}
