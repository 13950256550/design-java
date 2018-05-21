package com.aerodynamic.design.domain.moduleS2;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.aerodynamic.design.controller.AdminController;
import com.aerodynamic.design.domain.Business;
import com.aerodynamic.design.domain.moduleS2.readwritefile.WriteDataToFile;
import com.aerodynamic.design.domain.moduleS2.readwritefile.ReadInputFileData;

public class ModuleS2Business extends Business{

	@Override
	public void WriteDataToFile(Map map) {
		String path = AdminController.getWorkPath(this.getSessionid());
		File file = getModuleFile(path,fileMap.get("input_s2_1"));
		WriteDataToFile.WriteDataToFile(file, map);
	}

	@Override
	public Map parse() {
		String path = AdminController.getWorkPath(this.getSessionid());
		File file = getModuleFile(path,fileMap.get("input_s2_1"));
		List<String> list = readFile(file);
		return ReadInputFileData.parseS2_in(list);
	}

	@Override
	public Map parse(String fileid, List<String> list) {
		return ReadInputFileData.parseS2_in(list);
	}
}
