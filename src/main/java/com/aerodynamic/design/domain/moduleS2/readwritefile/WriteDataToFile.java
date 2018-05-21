package com.aerodynamic.design.domain.moduleS2.readwritefile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aerodynamic.design.controller.AdminController;
import com.aerodynamic.design.domain.fileprocess.Constant;
import com.aerodynamic.design.domain.fileprocess.GridDataUtil;
import com.aerodynamic.design.domain.moduleS2.constant.ControlVariableConstant;
import com.aerodynamic.design.utils.StringUtil;

public class WriteDataToFile {
	private static final Logger logger = LoggerFactory.getLogger(WriteDataToFile.class);
	public static void getGrid2(List<List<String>> grid2){
		for(int i=0;i<grid2.size();i++){
			List<String> row = grid2.get(i);
			row.remove(0);
		}
	}
	
	public static List<List<String>> getGrid4(List<List<String>> grid4){
		List<List<String>> result = new ArrayList<List<String>>();
		List<String> line = new ArrayList<String>();
		for(List<String> row:grid4){
			line.add(row.get(1));
		}
		result.add(line);
		//logger.info("line:{}",result);
		return result;
	}

	public static StringBuffer getBufferFromMap(Map map){
		StringBuffer sb = new StringBuffer();
		List<String> list = new ArrayList<String>();
		sb.append(map.get(ControlVariableConstant.TITLE题目[1])).append(Constant.newLine);
		
		//#1::TETAO,NPRK,KPP,DG,NM， KPST,JDK,PRK,NPR,ATP,MAH,PROD:
		list = new ArrayList<String>();
		list.add(String.valueOf(map.get(ControlVariableConstant.TETAO光顺系数[1])));
		list.add(String.valueOf(map.get(ControlVariableConstant.NPRK光顺次数[1])));
		list.add(String.valueOf(map.get(ControlVariableConstant.KPR误差打印[1])));
		list.add(String.valueOf(map.get(ControlVariableConstant.DG多步计算流量增量[1])));
		list.add(String.valueOf(map.get(ControlVariableConstant.NM最多步数[1])));
		list.add(String.valueOf(map.get(ControlVariableConstant.KPST每步打印[1])));
		list.add(String.valueOf(map.get(ControlVariableConstant.JDK分流前站序[1])));
		list.add(String.valueOf(map.get(ControlVariableConstant.PRK龙格法[1])));
		list.add(String.valueOf(map.get(ControlVariableConstant.NPR迭代次数[1])));
		list.add(String.valueOf(map.get(ControlVariableConstant.ATP摩擦系数[1])));
		list.add(String.valueOf(map.get(ControlVariableConstant.MAH多重网格[1])));
		list.add(String.valueOf(map.get(ControlVariableConstant.PROD继续计算控制[1])));
		sb.append(getLineFromList(list)).append(Constant.newLine);
		
		//#2： G,K,R,ALFD,DELTA,NROW,M,N,JSR,JFR,JSP,JFP:
		list = new ArrayList<String>();
		list.add(String.valueOf(map.get(ControlVariableConstant.G质量流量[1])));
		list.add(String.valueOf(map.get(ControlVariableConstant.K等熵指数[1])));
		list.add(String.valueOf(map.get(ControlVariableConstant.R气体常数[1])));
		list.add(String.valueOf(map.get(ControlVariableConstant.ALFD松弛系数[1])));
		list.add(String.valueOf(map.get(ControlVariableConstant.DELTA收敛精度[1])));
		list.add(String.valueOf(map.get(ControlVariableConstant.NROW叶排数[1])));
		list.add(String.valueOf(map.get(ControlVariableConstant.M计算站数[1])));
		list.add(String.valueOf(map.get(ControlVariableConstant.N流线数[1])));
		list.add(String.valueOf(map.get(ControlVariableConstant.JSP汇总表起站[1])));
		list.add(String.valueOf(map.get(ControlVariableConstant.JFR终站[1])));
		list.add(String.valueOf(map.get(ControlVariableConstant.JSP汇总表起站[1])));
		list.add(String.valueOf(map.get(ControlVariableConstant.JFP终站[1])));
		sb.append(getLineFromList(list)).append(Constant.newLine);
		
		//#3:KGP,WW,ICP,IDEV
		list = new ArrayList<String>();
		list.add(String.valueOf(map.get(ControlVariableConstant.KGP小流管流量分布[1])));
		list.add(String.valueOf(map.get(ControlVariableConstant.WW转向[1])));
		list.add(String.valueOf(map.get(ControlVariableConstant.ICP变比热[1])));
		list.add(String.valueOf(map.get(ControlVariableConstant.IDEV输入叶型角[1])));
		sb.append(getLineFromList(list)).append(Constant.newLine);
		
		//#4:JS1,JF1,JS2,JF2 为处在叶排进出口边界的计算站序
		list = new ArrayList<String>();
		list = (List<String>)map.get("ControlVariable.line1");
		sb.append(getLineFromList(list)).append(Constant.newLine);
		
		//#5:H2(1:L1),(L1>2)
		//#6:PIN(1:L1)
		//#7:TIN(1:L1)
		//#8:VUIN(1:L1)
		//#9:HF0(1:L2)
		//#10： SIG0(1:L2)-
		List<List<String>> grid1 = (List<List<String>>)map.get("ControlVariable.grid1");
		grid1 = GridDataUtil.transform(grid1);
		writeGridToStringBuffer(sb,grid1);
		
		//#11:MUH,MUM,MUT,SPV,FPV,IDF,PB:
		list = new ArrayList<String>();
		list.add(String.valueOf(map.get(ControlVariableConstant.MUH根[1])));
		list.add(String.valueOf(map.get(ControlVariableConstant.MUM中[1])));
		list.add(String.valueOf(map.get(ControlVariableConstant.MUT尖[1])));
		list.add(String.valueOf(map.get(ControlVariableConstant.SPV超音解站范围起始站[1])));
		list.add(String.valueOf(map.get(ControlVariableConstant.FPV终止站[1])));
		list.add(String.valueOf(map.get(ControlVariableConstant.IDF分流尾站序[1])));
		list.add(String.valueOf(map.get(ControlVariableConstant.LPB计算站形状[1])));
		sb.append(getLineFromList(list)).append(Constant.newLine);
		
		//#12:KPV— 根部流路轮廓上的基准点的数目，3≤ KPV≤ 40,如KPV=0 则按计算站输入流路， 此时在程序中KPV=M。
		sb.append(map.get(ControlVariableConstant.KPV根部流路[1])).append(Constant.newLine);
		
		List<List<String>> grid = (List<List<String>>)map.get("ControlVariable.grid2");
		getGrid2(grid);
		grid = GridDataUtil.transform(grid);
		int rowCount = 0;
		//#13-1,13-2… RHUB(1:KPV)一根部流路基准点(或计算站)的半径坐标， mm。
		sb.append(getLineFromList(grid.get(rowCount))).append(Constant.newLine);
		//#14-1,14-2… ZHUB(1:KPV)— 根部流路基准点(或计算站)的轴向坐标， mm。
		rowCount++;
		sb.append(getLineFromList(grid.get(rowCount))).append(Constant.newLine);
		
		int kpv = (int)Double.parseDouble(String.valueOf(map.get(ControlVariableConstant.KPV根部流路[1])));
		if(kpv!=0){
			//#15-1,15-2… :KPV=0 不输,ZHUBS(1:M)— 根部流路沿Z 轴所有计算站的Z 坐标
			rowCount++;
			sb.append(getLineFromList(grid.get(rowCount))).append(Constant.newLine);
		}
		
		//#16:KPP— 尖部流路轮廓上的基准点的数
		sb.append(map.get(ControlVariableConstant.KPP尖部流路[1])).append(Constant.newLine);
		
		//#17-1,17-2… RTIP(1:KPP)— 尖部流路基准点(或计算站)的半径坐标， mm。
		rowCount++;
		sb.append(getLineFromList(grid.get(rowCount))).append(Constant.newLine);
		//#18-1,18-2… ZTIP(1:KPP)— 尖部流路基准点(或计算站)的轴向坐标， mm。
		rowCount++;
		sb.append(getLineFromList(grid.get(rowCount))).append(Constant.newLine);
		
		int kpp = (int)Double.parseDouble(String.valueOf(map.get(ControlVariableConstant.KPV根部流路[1])));
		if(kpp!=0){
			//#19-1,19-2… ：KPP=0 不输，ZTIPS(1:M)— 尖部流路沿Z 轴所有计算站的Z 坐标
			rowCount++;
			sb.append(getLineFromList(grid.get(rowCount))).append(Constant.newLine);
		}
		
		String lpb = String.valueOf(map.get(ControlVariableConstant.LPB计算站形状[1]));
		if(!"0".equals(lpb)){
			if("1".equals(lpb)){
				//#20-1,20-2,… ：PB=1 时输,RMID(1:M)-各计算站叶中R 坐标，mm。
				rowCount++;
				sb.append(getLineFromList(grid.get(rowCount))).append(Constant.newLine);
				//#21-1,21-2,… ：PB=1 时输,ZMID(1:M)-各计算站叶中Z 坐标，mm。
				rowCount++;
				sb.append(getLineFromList(grid.get(rowCount))).append(Constant.newLine);
			}else{
				Map<String,Object> scatterPointsMap = (Map<String,Object>)map.get("ControlVariable.scatterPointsMap");
				if(scatterPointsMap!=null){
					for(int i=0;i<scatterPointsMap.size();i++){
						Map<String,Object> indexMap = (Map<String,Object>)scatterPointsMap.get(String.valueOf(i+1));
						list = new ArrayList<String>();
						list.add(String.valueOf(indexMap.get("nls")));
						sb.append(getLineFromList(list)).append(Constant.newLine);
						
						List<List<String>> scatterPointGrid = (List<List<String>>)indexMap.get("grid");
						scatterPointGrid = GridDataUtil.transform(scatterPointGrid);
						writeGridToStringBuffer(sb,scatterPointGrid);
					}
					if("2".equals(lpb)){
						//#20-1,20-2… ： PB=2 时输。
					}
				}
			}
		}
		
		//#22-1， 22-2:KBK(1:M)-各计算站附面层堵塞因子， KBK>1.0
		rowCount++;
		sb.append(getLineFromList(grid.get(rowCount))).append(Constant.newLine);
		
		int nrow = (int)Double.parseDouble(String.valueOf(map.get(ControlVariableConstant.NROW叶排数[1])));
		Map<String,Object> nrowMap = (Map<String,Object>)map.get("ControlVariable.nrowMap");
		for(int i=0;i<nrow;i++){
			Map<String,Object> indexMap = (Map<String,Object>)nrowMap.get(String.valueOf(i+1));
			//#R1:IZ,RMH,RMM,RMT,QFH,QFM,QFT,DIZ,GRL,GRT,DW,W
			list = new ArrayList<String>();
			list.add(String.valueOf(indexMap.get(ControlVariableConstant.IZ叶片数[1])));
			list.add(String.valueOf(indexMap.get(ControlVariableConstant.RMH根[1])));
			list.add(String.valueOf(indexMap.get(ControlVariableConstant.RMM中[1])));
			list.add(String.valueOf(indexMap.get(ControlVariableConstant.RMT尖[1])));
			list.add(String.valueOf(indexMap.get(ControlVariableConstant.QFH根[1])));
			list.add(String.valueOf(indexMap.get(ControlVariableConstant.QFM中[1])));
			list.add(String.valueOf(indexMap.get(ControlVariableConstant.QFT尖[1])));
			list.add(String.valueOf(indexMap.get(ControlVariableConstant.DIZ多步计算叶片数增量[1])));
			list.add(String.valueOf(indexMap.get(ControlVariableConstant.GRL绘图控制[1])));
			list.add(String.valueOf(indexMap.get(ControlVariableConstant.GRT[1])));
			list.add(String.valueOf(indexMap.get(ControlVariableConstant.DW角速度增量[1])));
			list.add(String.valueOf(indexMap.get(ControlVariableConstant.W转动角速度[1])));
			sb.append(getLineFromList(list)).append(Constant.newLine);
			
			//#R2:PX,NL,KH,KG,TAU1,TAU2,TAU3,PB,PQ,QSH,QSM,QST
			list = new ArrayList<String>();
			list.add(String.valueOf(indexMap.get(ControlVariableConstant.PX叶片力计入控制[1])));
			list.add(String.valueOf(indexMap.get(ControlVariableConstant.NL计算步数[1])));
			list.add(String.valueOf(indexMap.get(ControlVariableConstant.KH增压系数[1])));
			list.add(String.valueOf(indexMap.get(ControlVariableConstant.KG流量系数[1])));
			list.add(String.valueOf(indexMap.get(ControlVariableConstant.TAU1前跟[1])));
			list.add(String.valueOf(indexMap.get(ControlVariableConstant.TAU2轴中[1])));
			list.add(String.valueOf(indexMap.get(ControlVariableConstant.TAU3尾尖[1])));
			list.add(String.valueOf(indexMap.get(ControlVariableConstant.PB解题形式识[1])));
			list.add(String.valueOf(indexMap.get(ControlVariableConstant.PQ功分布标识[1])));
			list.add(String.valueOf(indexMap.get(ControlVariableConstant.QSH根[1])));
			list.add(String.valueOf(indexMap.get(ControlVariableConstant.QSM中[1])));
			list.add(String.valueOf(indexMap.get(ControlVariableConstant.QST尖[1])));
			sb.append(getLineFromList(list)).append(Constant.newLine);
			
			List<List<String>> grid3 = (List<List<String>>)indexMap.get("ControlVariable.grid3");
			grid3 = GridDataUtil.transform(grid3);
			//#R3:DTH(1:L3)-该叶排各站的叶片根部周向厚度， mm。
			//#R4:DTM(1:L3)-该叶排各站的叶片叶中周向厚度， mm。
			//#R5:DTT(1:L3)-该叶排各站的叶片尖部周向厚度， mm。
			//#R6:HFR(1:L4)，(3≤ L4≤ 12)-在叶排出口边界上点在根尖流路点连线上的长度（ 由根算起） 坐标
			//#R7:SIGS-在叶排出口边界HFR 点上的总压恢复系数值。
			//#R8:VUS-在叶排出口边界HFR 点上的周向分速度值（ PB=0),m/s。
			writeGridToStringBuffer(sb,grid3);
			
			int nm = (int)Double.parseDouble(String.valueOf(map.get(ControlVariableConstant.NM最多步数[1])));
			if(nm!=0){
				//#R9:NM=0 不输,DSIG-在釆用连续多步计算时SIGS 的总增量。
				//#R10:DVU,DALF,NM=0 不输，
			}
		}
		
		//#23:KGP=0 不输， GP(1:N)-每一流线下通过的流量与总流量之比。
		String kgp = String.valueOf(map.get(ControlVariableConstant.KGP小流管流量分布[1]));
		if(!"0".equals(kgp)){
			List<List<String>> grid4 = (List<List<String>>)map.get("ControlVariable.grid4");
			grid4 = getGrid4(grid4);
			//grid4 = GridDataUtil.transform(grid4);
			sb.append(getLineFromList(grid4.get(0))).append(Constant.newLine);
		}
		
		return sb;
	}
	
	public static String getLineFromList(List<String> list){
		StringBuffer sb = new StringBuffer();
		if(list!=null){
			int count = 0;
			for(String col:list){
				if(col==null || "null".equals(col)){
					col = "";
				}
				sb.append(StringUtil.rightPad(col, 6));
				count++;
				if(count==12){
					sb.append(Constant.newLine);
					count = 0;
				}
			}
		}
		String line = sb.toString();
		if(line.endsWith(Constant.newLine)){
			line = line.substring(0,line.length()-Constant.newLine.length());
		}
		return line;
	}
	
	public static void writeGridToStringBuffer(StringBuffer sb,List<List<String>> grid){
		if(grid!=null){
			for(List<String> row:grid){
				sb.append(getLineFromList(row)).append(Constant.newLine);
			}
		}
	}
	
	public static void writeStringToStream(String data, OutputStream os) {
		byte[] buffer = null;
		try {
			buffer = data.getBytes();
			os.write(buffer, 0, buffer.length);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public static void writeStringBuffserToStream(StringBuffer buffer,OutputStream os) {
		writeStringToStream(buffer.toString(), os);
		buffer.delete(0, buffer.length());
	}
	
	public static void WriteDataToFile(File file,Map map){
		StringBuffer sb = getBufferFromMap(map);
		OutputStream os = null;
		try {
			os = new FileOutputStream(file);
			writeStringBuffserToStream(sb,os);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally{
			if(os!=null){
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
