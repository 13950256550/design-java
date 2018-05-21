package com.aerodynamic.design.domain.module1D;

import java.util.HashMap;
import java.util.Map;

import com.aerodynamic.design.domain.CodeList;
import com.aerodynamic.design.domain.module1D.constant.ControlVariableConstant;
import com.aerodynamic.design.domain.module1D.constant.DesignProblemConstant;
import com.aerodynamic.design.domain.module1D.constant.FeaturesCalculateConstant;

public class Module1DCodeList{
	public static Map<String,CodeList> codeListMap = new HashMap<String,CodeList>();
	static{
		//控制变量
		String name = ControlVariableConstant.解题类型_K[0];
		CodeList codeList = new CodeList(name,new String[][]{
													new String[]{"1","设计或检查"},
													new String[]{"2","特性"},
													new String[]{"3","(1+2)"},
													new String[]{"4","(3+展旋比)"}
												});
		codeListMap.put(name, codeList);
		
		name = ControlVariableConstant.检查或设计_K12[0];
		codeList = new CodeList(name,new String[][]{
											new String[]{"1","设计问题"},
											new String[]{"2","检查问题"}
										});
		codeListMap.put(name, codeList);
		
		name = ControlVariableConstant.损失修正符_IZI[0];
		codeList = new CodeList(name,new String[][]{
											new String[]{"0","不修正"},
											new String[]{"1","修正"}
										});
		codeListMap.put(name, codeList);
		
		name = ControlVariableConstant.静叶可调_IREG[0];
		codeList = new CodeList(name,new String[][]{
											new String[]{"0","不调"},
											new String[]{"1","调静叶并输入"}
										});
		codeListMap.put(name, codeList);
		
		name = ControlVariableConstant.流路转换_IZX[0];
		codeList = new CodeList(name,new String[][]{
											new String[]{"0","不生成GAZD.D"},
											new String[]{"1","站为竖直线"},
											new String[]{"2","尖根同修(按DGAZD.D)"},
											new String[]{"3","只修尖部(按DGAZD.D)"}
										});
		codeListMap.put(name, codeList);
		
		name = ControlVariableConstant.叶排轴向长度缩放_IDX[0];
		codeList = new CodeList(name,new String[][]{
											new String[]{"1","不缩放"},
											new String[]{"2","按DGAZD.D缩放"}
										});
		codeListMap.put(name, codeList);
		
		name = ControlVariableConstant.性能参数分布_IPE[0];
		codeList = new CodeList(name,new String[][]{
											new String[]{"1","不修正"},
											new String[]{"2","分段修正"},
											new String[]{"3","按E3减幅修正"},
											new String[]{"4","按E3模式修正"}
										});
		codeListMap.put(name, codeList);
		
		name = ControlVariableConstant.叶型参数分布_IFH[0];
		codeList = new CodeList(name,new String[][]{
											new String[]{"0","不生成DROW**.D"},
											new String[]{"1","不修正"},
											new String[]{"2","按E3模式修正"}
										});
		codeListMap.put(name, codeList);
		
		name = ControlVariableConstant.叶排内设站_INZ[0];
		codeList = new CodeList(name,new String[][]{
											new String[]{"0","叶排内1站"},
											new String[]{"1","按DGAZD.D给定设站"}
										});
		codeListMap.put(name, codeList);
		
		//设计问题
		name = DesignProblemConstant.KF叶型标识[0];
		codeList = new CodeList(name,new String[][]{
					new String[]{"1","BC10"},
					new String[]{"2","C4"},
					new String[]{"3","NACA65"}
				});
		codeListMap.put(name, codeList);
		
		name = DesignProblemConstant.KC压气机类型[0];
		codeList = new CodeList(name,new String[][]{
					new String[]{"1","单压气机"},
					new String[]{"2","单涵双轴中的低压"},
					new String[]{"3","单涵双轴中的高压"},
					new String[]{"4","双涵中的风扇"},
					new String[]{"5","双涵中的增压机"},
					new String[]{"6","三轴中的中压,双涵"},
					new String[]{"7","双涵中的高压"},
					new String[]{"8","三轴中的高压"}
				});
		codeListMap.put(name, codeList);
		
		name = DesignProblemConstant.KPATH流路输入标识[0];
		codeList = new CodeList(name,new String[][]{
					new String[]{"0","输入等外、中、内径，或给定进口或出口内外径"},
					new String[]{"1","输入各级转子进口外径，压气机出口外径"},
					new String[]{"2","输入各级转子进口中径，压气机出口中径"},
					new String[]{"3","各级转子进口内径，压气机出口内径"}
				});
		codeListMap.put(name, codeList);
		
		//特性计算
		name = FeaturesCalculateConstant.控制参数KGKA[0];
		codeList = new CodeList(name,new String[][]{
					new String[]{"0","特性(2)不输"},
					new String[]{"1","特性(2)输"}
				});
		codeListMap.put(name, codeList);	
		
		name = FeaturesCalculateConstant.控制参数IGKA[0];
		codeList = new CodeList(name,new String[][]{
					new String[]{"0","特性(3)不输"},
					new String[]{"1","特性(3)输"}
				});
		codeListMap.put(name, codeList);
		
		name = FeaturesCalculateConstant.转速和状态点控制IVAR1[0];
		codeList = new CodeList(name,new String[][]{
					new String[]{"0","全部计算"},
					new String[]{"1","计算单点(QL)"},
					new String[]{"2","垂直段单点(PR2)"}
				});
		codeListMap.put(name, codeList);
		
		name = FeaturesCalculateConstant.状态点计算控制IQP[0];
		codeList = new CodeList(name,new String[][]{
					new String[]{"0","全部计算"},
					new String[]{"1","计算单点(QL)"},
					new String[]{"2","垂直段单点(PR2)"}
				});
		codeListMap.put(name, codeList);

		name = FeaturesCalculateConstant.搜索共同工作点IZAP[0];
		codeList = new CodeList(name,new String[][]{
					new String[]{"0","搜索"},
					new String[]{"1","不搜索"}
				});
		codeListMap.put(name, codeList);
	}
}
