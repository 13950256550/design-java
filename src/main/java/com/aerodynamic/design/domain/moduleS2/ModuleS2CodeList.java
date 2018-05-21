package com.aerodynamic.design.domain.moduleS2;

import java.util.HashMap;
import java.util.Map;

import com.aerodynamic.design.domain.CodeList;
import com.aerodynamic.design.domain.moduleS2.constant.ControlVariableConstant;

public class ModuleS2CodeList{
	public static Map<String,CodeList> codeListMap = new HashMap<String,CodeList>();
	static{
		//控制变量总参数
		String name = ControlVariableConstant.KPR误差打印[0];
		CodeList codeList = new CodeList(name,new String[][]{
													new String[]{"1.","每步计算屏幕输出"},
													new String[]{"2.","每步计算文件输出"},
													new String[]{"3.","两者均输出"}
												});
		codeListMap.put(name, codeList);
		
		name = ControlVariableConstant.PROD继续计算控制[0];
		codeList = new CodeList(name,new String[][]{
											new String[]{"0.","不继续计算"},
											new String[]{"1.","起始计算"},
											new String[]{"2.","继续计算"}
										});
		codeListMap.put(name, codeList);
		
		name = ControlVariableConstant.KGP小流管流量分布[0];
		codeList = new CodeList(name,new String[][]{
											new String[]{"0.0","有程序内定分布"},
											new String[]{"1.0","按输入的GP分布"}
										});
		codeListMap.put(name, codeList);
		
		name = ControlVariableConstant.WW转向[0];
		codeList = new CodeList(name,new String[][]{
											new String[]{"1.0","后视逆时针旋转"},
											new String[]{"-1.0","后视顺时针旋转"}
										});
		codeListMap.put(name, codeList);
		
		name = ControlVariableConstant.ICP变比热[0];
		codeList = new CodeList(name,new String[][]{
											new String[]{"0","常值比热"},
											new String[]{"1","变热比"}
										});
		codeListMap.put(name, codeList);
		
		name = ControlVariableConstant.LPB计算站形状[0];
		codeList = new CodeList(name,new String[][]{
											new String[]{"0","直线计算站"},
											new String[]{"1","抛物线计算站"},
											new String[]{"2","离散点计算站"},
											new String[]{"3","进出口计算"}
										});
		codeListMap.put(name, codeList);
		
		name = ControlVariableConstant.PQ功分布标识[0];
		codeList = new CodeList(name,new String[][]{
											new String[]{"0","指数规律"},
											new String[]{"1","三次多项式规律"}
										});
		codeListMap.put(name, codeList);
		
		name = ControlVariableConstant.PB解题形式识[0];
		codeList = new CodeList(name,new String[][]{
											new String[]{"0","解反问题"},
											new String[]{"1","解正问题"}
										});
		codeListMap.put(name, codeList);
		
		name = ControlVariableConstant.PX叶片力计入控制[0];
		codeList = new CodeList(name,new String[][]{
											new String[]{"0.","不计叶片力,或流场和造型联算时"},
											new String[]{"1.","X=流面角"},
											new String[]{"2.","X在前缘,轴线,尾缘为常值"},
											new String[]{"3.","X在根,中,尖为常值"},
											new String[]{"40.","第一次与叶片造型迭代中"},
											new String[]{"51-70","与叶片造型迭代中"}
										});
		codeListMap.put(name, codeList);
		
		name = ControlVariableConstant.出入站类型[0];
		codeList = new CodeList(name,new String[][]{
											new String[]{"0",""},
											new String[]{"1","入口站"},
											new String[]{"2","出口站"}
										});
		codeListMap.put(name, codeList);

	}
}
