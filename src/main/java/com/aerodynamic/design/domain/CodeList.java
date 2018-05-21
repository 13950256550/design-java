package com.aerodynamic.design.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodeList {
	public static Map<String,CodeList> codeListMap = new HashMap<String,CodeList>();
	static{
		/*for(String key:Module1DCodeList.codeListMap.keySet()){
			codeListMap.put(key, Module1DCodeList.codeListMap.get(key));
		}
		
		for(String key:ModuleS2CodeList.codeListMap.keySet()){
			codeListMap.put(key, ModuleS2CodeList.codeListMap.get(key));
		}*/
	}
	private String codeName;
	private List<Code> codeList;
	
	public CodeList(String codeName, List<Code> codeList) {
		this.codeName = codeName;
		this.codeList = codeList;
	}

	public CodeList(String codeName,Code[] codes) {
		this.codeName = codeName;
		this.codeList = new ArrayList<Code>();
		for(Code code:codes){
			this.codeList.add(code);
		}
	}
	
	public CodeList(String codeName,String[][] codes) {
		this.codeName = codeName;
		this.codeList = new ArrayList<Code>();
		for(String[] code:codes){
			Code aCode = new Code(code[0],code[1]);
			this.codeList.add(aCode);
		}
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public List<Code> getCodeList() {
		return codeList;
	}

	public void setCodeList(List<Code> codeList) {
		this.codeList = codeList;
	}
	
	public static CodeList getCodeListByName(String name){
		return codeListMap.get(name);
	}
	
	public static boolean isCodeList(String id){
		boolean result = false;
		CodeList codeList = getCodeListByName(id);
		if(codeList!=null){
			result = true;
		}
		return result;
	}
	
}
