package com.aerodynamic.design.domain.module1D;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aerodynamic.design.controller.AdminController;
import com.aerodynamic.design.domain.Business;
import com.aerodynamic.design.domain.module1D.readwritefile.ReadInputFileData;
import com.aerodynamic.design.domain.module1D.readwritefile.WriteDataToFile;

public class Module1DBusiness extends Business{
	@Override
	public void WriteDataToFile(Map map) {
		String path = AdminController.getWorkPath(this.getSessionid());
		File file = getModuleFile(path,fileMap.get("input_1d_1"));
		WriteDataToFile.WriteDataToFile(file, map);
		
		file = getModuleFile(path,fileMap.get("input_1d_2"));
		WriteDataToFile.WriteDataToFile2(file,map);
	}

	@Override
	public Map parse() {
		String path = AdminController.getWorkPath(this.getSessionid());
		File file = getModuleFile(path,fileMap.get("input_1d_1"));
		List<String> list = readFile(file);
		Map map = ReadInputFileData.parse1D_in1(list);
		
		file = getModuleFile(path,fileMap.get("input_1d_2"));
		list = readFile(file);
		ReadInputFileData.parse1D_in2(list, map);
		return map;
	}

	@Override
	public Map parse(String fileid, List<String> list) {
		Map result = new HashMap();
		if("input_1d_1".equals(fileid)){
			result = ReadInputFileData.parse1D_in1(list);
		}else if("input_1d_2".equals(fileid)){ 
			String path = AdminController.getWorkPath(this.getSessionid());
			File file = getModuleFile(path,fileMap.get("input_1d_1"));
			List<String> list1 = readFile(file);
			result = ReadInputFileData.parse1D_in1(list1);
			ReadInputFileData.parse1D_in2(list, result);
		}
		return result;
	}
}
