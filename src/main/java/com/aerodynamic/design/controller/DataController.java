package com.aerodynamic.design.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aerodynamic.design.domain.Business;
import com.aerodynamic.design.domain.fileprocess.Constant;
import com.aerodynamic.design.domain.fileprocess.GridDataUtil;
import com.aerodynamic.design.domain.fileprocess.ReadDataFile;
import com.aerodynamic.design.domain.module1D.constant.ControlVariableConstant;
import com.aerodynamic.design.domain.module1D.constant.DesignProblemConstant;
import com.aerodynamic.design.domain.module1D.readwritefile.ReadInputFileData;
import com.aerodynamic.design.domain.module1D.readwritefile.ReadOutputFileData;
import com.aerodynamic.design.domain.module1D.readwritefile.WriteDataToFile;
import com.aerodynamic.design.utils.StringUtil;

@Controller
@RequestMapping("/api")
public class DataController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	public static Map<String,Map<String,Map>> CONTEXT = new HashMap<String,Map<String,Map>>();

	@RequestMapping("/data/{fileid}/{sessionid}")
	@ResponseBody
    public Map getDataById(@PathVariable String fileid,@PathVariable String sessionid){
    	return getMap(fileid,sessionid);  
    }
	
	@RequestMapping("/codelist/{module}")
	@ResponseBody
    public Map getCodeList(@PathVariable String module){
    	return Business.getCodeList(module);  
    }
	
	@RequestMapping("/getFile/{fileid}/{sessionid}")
	@ResponseBody
    public Map getFile(@PathVariable String fileid,@PathVariable String sessionid){
		Business business = Business.getBusiness(fileid,sessionid);
		List<String> list = business.readFile(fileid);
		StringBuilder sb = new StringBuilder();
		for(String line:list){
			sb.append(line).append(Constant.newLine);
		}
		Map map = new HashMap();
		map.put("data", sb.toString());
		Map result = new HashMap();
		result.put(fileid, map);
    	return result;
    }
	
	@PostMapping(path = "/updateData/{fileid}/{sessionid}")
	@ResponseBody
    public Map saveData(@PathVariable String fileid,@PathVariable String sessionid,@RequestBody Map map){
		Map newMap = Business.getMap(map);
		logger.info("data:\r\n{},\r\ndata2:\r\n{}",map,newMap);
		Business business = Business.getBusiness(fileid,sessionid);
		business.WriteDataToFile(newMap);
		
		Map moduleMap = null;
		moduleMap = CONTEXT.get(sessionid);
		if(moduleMap==null){
			moduleMap = new HashMap<String,Map<String,Object>>();
		}
		
		moduleMap.put(fileid, business.parse());
		
		StringBuilder sb = new StringBuilder();
		List<String> list = business.readFile();
		for(String line:list){
			sb.append(line).append(Constant.newLine);
		}
		Map fileMap = new HashMap();
		fileMap.put("data", sb.toString());
		
		Map result = new HashMap();
		result.put(fileid, fileMap);
    	return result;
    }
	
	@PostMapping(path = "/updateFile/{fileid}/{sessionid}")
	@ResponseBody
    public Map updateFile(@PathVariable String fileid,@PathVariable String sessionid,@RequestBody Map map){
		//logger.info("map{}",map);
		String data = String.valueOf(((Map)map.get(fileid)).get("data"));
		String[] lines = data.split(Constant.newLine);
		List<String> list = new ArrayList();
		for(String line : lines){
			list.add(line);
		}
		
		Map moduleMap = CONTEXT.get(sessionid);
		if(moduleMap==null){
			moduleMap = new HashMap();
			CONTEXT.put(sessionid, moduleMap);
		}
		Business business = Business.getBusiness(fileid,sessionid);
		Map newMap = new HashMap();
		newMap = business.parse(fileid,list);
		moduleMap.put(fileid,newMap);
		
		String path = AdminController.getWorkPath(sessionid);
		File file = business.getModuleFile(path,fileid);
		StringUtil.writeStringToFile(data, file);

    	return newMap;
    }
	/*
	public static String getModuleName(String fileid){
		String fileName = fileMap.get(fileid);
		String module = "";
		if(fileName!=null){
			module = fileName.split("/")[0];
		}
		return module;
	}
	*/
	public Map<String,Object> getMap(String fileid,String sessionid){
		Map<String,Object> result = null;
		Map<String,Map> map = CONTEXT.get(sessionid);
		if(map==null){
			map = new HashMap<String,Map>();
			CONTEXT.put(sessionid, map);
		}else{
			result = map.get(fileid);
		}
		
		if(result==null || result.size()==0){
			Business business = Business.getBusiness(fileid,sessionid);
			result = business.parse();
			map.put(fileid, result);
		}
		
		return result;
	}
	
	/*
	public static File getModuleFile(String fileName){
		File file = new File(AdminController.workPath+"/"+fileName);
		return file;
	}
	
	public static File getModuleFile(String workPath,String fileid){
		String fileName = fileMap.get(fileid);
		if(fileName==null || "".equals(fileName)){
			fileName = fileid;
		}
		File file = new File(workPath+"/"+fileName);
		return file;
	}
	*/
	
	//输出文件数据获取
	@RequestMapping(path = "/getAeroData/{sessionid}")
	@ResponseBody
	public List getAeroData(@PathVariable String sessionid){
		List result = new ArrayList();
		String path = AdminController.getWorkPath(sessionid);
		File file = Business.getModuleFile(path,"1D/aero.dat");
		List<List<String>> gridData = ReadOutputFileData.readAeroData(file);
		gridData = GridDataUtil.transform(gridData);

		List<String> x = gridData.get(0);
		x.remove(x.size()-1);
		
		for(int i=1;i<gridData.size();i++){
			Map map = new HashMap();
			List<String> row = gridData.get(i);
			String key = row.remove(row.size()-1);
			List<List<String>> data = new ArrayList<List<String>>();
			for(int j=0;j<row.size();j++){
				List<String> temp = new ArrayList<String>();
				temp.add(x.get(j));
				temp.add(row.get(j));
				data.add(temp);
			}
			map.put("key", key);
			map.put("data", data);
			result.add(map);
		}
		return result;
	}
	
	//输出文件数据获取
	@RequestMapping(path = "/getFoilData/{sessionid}")
	@ResponseBody
	public List getFoilData(@PathVariable String sessionid){
		List result = new ArrayList();
		String path = AdminController.getWorkPath(sessionid);
		File file = Business.getModuleFile(path,"1D/foil.dat");
		List<List<String>> gridData = ReadOutputFileData.readFoilData(file);
		gridData = GridDataUtil.transform(gridData);

		List<String> x = gridData.get(0);
		x.remove(x.size()-1);
		
		for(int i=1;i<gridData.size();i++){
			Map map = new HashMap();
			List<String> row = gridData.get(i);
			String key = row.remove(row.size()-1);
			List<List<String>> data = new ArrayList<List<String>>();
			for(int j=0;j<row.size();j++){
				List<String> temp = new ArrayList<String>();
				temp.add(x.get(j));
				temp.add(row.get(j));
				data.add(temp);
			}
			map.put("key", key);
			map.put("data", data);
			result.add(map);
		}
		return result;
	}
	
	//输出文件数据获取
	@RequestMapping(path = "/getPathData/{sessionid}")
	@ResponseBody
	public List getPathData(@PathVariable String sessionid){
		String path = AdminController.getWorkPath(sessionid);
		File file = Business.getModuleFile(path,"1D/path");
		List<List<List<String>>> gridData = ReadOutputFileData.readPathData(file);

		return gridData;
	}
	
	//输出文件数据获取
	@RequestMapping(path = "/getPrefData/{sessionid}")
	@ResponseBody
	public Map getPrefData(@PathVariable String sessionid){
		Map result = new HashMap();
		
		String path = AdminController.getWorkPath(sessionid);
		File file = Business.getModuleFile(path,"1D/perf1.dat");
		List<List<String>> gridData = ReadOutputFileData.readPerfData(file);
		gridData = GridDataUtil.transform(gridData);
		
		List<List<List<String>>> chart1 = new ArrayList<List<List<String>>>();
		for(int rownum=0;rownum<gridData.size();rownum=rownum+4){
			List<List<String>> chart1Line = new ArrayList<List<String>>();
			List<String> line1 = gridData.get(rownum+1);
			List<String> line2 = gridData.get(rownum+3);
			for(int colnum=0;colnum<line1.size();colnum++){
				List<String> point = new ArrayList<String>();
				point.add(line1.get(colnum));
				point.add(line2.get(colnum));
				chart1Line.add(point);
			}
			chart1.add(chart1Line);
		}
		result.put("chart1", chart1);
		
		List<List<List<String>>> chart2 = new ArrayList<List<List<String>>>();
		List<List<String>> chart2Line = new ArrayList<List<String>>();
		
		List<List<String>> surgData = ReadOutputFileData.readSurgData(path);
		for(List<String> row:surgData){
			List<String> point = new ArrayList<String>();
			point.add(row.get(0));
			point.add(row.get(1));
			chart2Line.add(point);
		}
		chart2.add(chart2Line);
		
		List<List<String>> operData = ReadOutputFileData.readOperData(path);
		chart2Line = new ArrayList<List<String>>();
		for(List<String> row:operData){
			List<String> point = new ArrayList<String>();
			point.add(row.get(0));
			point.add(row.get(1));
			chart2Line.add(point);
		}
		chart2.add(chart2Line);
		
		for(int rownum=0;rownum<gridData.size();rownum=rownum+4){
			chart2Line = new ArrayList<List<String>>();
			List<String> line1 = gridData.get(rownum+1);
			List<String> line2 = gridData.get(rownum+2);
			for(int colnum=0;colnum<line1.size();colnum++){
				List<String> point = new ArrayList<String>();
				point.add(line1.get(colnum));
				point.add(line2.get(colnum));
				chart2Line.add(point);
			}
			chart2.add(chart2Line);
		}
		result.put("chart2", chart2);
		
		List<List<List<String>>> chart3 = new ArrayList<List<List<String>>>();
		List<List<String>> chart3Line = null;

		for(int rownum=0;rownum<gridData.size();rownum=rownum+4){
			chart3Line = new ArrayList<List<String>>();
			List<String> line1 = gridData.get(rownum+3);
			List<String> line2 = gridData.get(rownum+2);
			for(int colnum=0;colnum<line1.size();colnum++){
				List<String> point = new ArrayList<String>();
				point.add(line1.get(colnum));
				point.add(line2.get(colnum));
				chart3Line.add(point);
			}
			chart3.add(chart3Line);
		}
		result.put("chart3", chart3);

		return result;
	}
}
