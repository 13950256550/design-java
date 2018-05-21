package com.aerodynamic.design.domain.moduleS2.readwritefile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aerodynamic.design.domain.InputItem;
import com.aerodynamic.design.domain.fileprocess.GridDataUtil;
import com.aerodynamic.design.domain.fileprocess.ReadDataFile;
import com.aerodynamic.design.domain.moduleS2.constant.ControlVariableConstant;

public class ReadInputFileData extends ReadDataFile{
	public static void main(String[] args) {
		Map<String,Object> map = parseS2_in("/S2/S2_in");
		System.out.println(map);
	}
	public static Map<String,Object> parseS2_in(String path){
		List<String> datas = ReadInputFileData.readFile(path);
		return parseS2_in(datas);
	}
	
	public static void put(Map<String,Object> map,String[] id,String value){
		map.put(id[0], new InputItem(id[0],id[1],value));
	}
	
	public static String getString(Map<String,Object> map,String key){
		InputItem item = (InputItem)map.get(key);
		return item!=null?item.getValue():"";
	}
	
	public static int getInteger(Map<String,Object> map,String key){
		InputItem item = (InputItem)map.get(key);
		int value = 0;
		if(item!=null){
			try {
				value = (int) Double.parseDouble(item.getValue());
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return value;
	}
	
	public static Map<String,Object> parseS2_in(List<String> list){
		Map<String,Object> result = new HashMap<String,Object>();
		String[] arrayString = null;
		List<List<String>> grid = null;
		int row = 0;
		//TITLE-作业题目
		put(result,ControlVariableConstant.TITLE题目, list.get(row));
		
		//#1::TETAO,NPRK,KPP,DG,NM， KPST,JDK,PRK,NPR,ATP,MAH,PROD:
		row++;
		arrayString = spliteRow(list.get(row),6,12);
		put(result,ControlVariableConstant.TETAO光顺系数, arrayString[0]);
		put(result,ControlVariableConstant.NPRK光顺次数, arrayString[1]);
		put(result,ControlVariableConstant.KPR误差打印, arrayString[2]);
		put(result,ControlVariableConstant.DG多步计算流量增量, arrayString[3]);
		put(result,ControlVariableConstant.NM最多步数, arrayString[4]);
		put(result,ControlVariableConstant.KPST每步打印, arrayString[5]);
		put(result,ControlVariableConstant.JDK分流前站序, arrayString[6]);
		put(result,ControlVariableConstant.PRK龙格法, arrayString[7]);
		put(result,ControlVariableConstant.NPR迭代次数, arrayString[8]);
		put(result,ControlVariableConstant.ATP摩擦系数, arrayString[9]);
		put(result,ControlVariableConstant.MAH多重网格, arrayString[10]);
		put(result,ControlVariableConstant.PROD继续计算控制, arrayString[11]);
		
		//#2： G,K,R,ALFD,DELTA,NROW,M,N,JSR,JFR,JSP,JFP:
		row++;
		arrayString = spliteRow(list.get(row),6,12);
		put(result,ControlVariableConstant.G质量流量, arrayString[0]);
		put(result,ControlVariableConstant.K等熵指数, arrayString[1]);
		put(result,ControlVariableConstant.R气体常数, arrayString[2]);
		put(result,ControlVariableConstant.ALFD松弛系数, arrayString[3]);
		put(result,ControlVariableConstant.DELTA收敛精度, arrayString[4]);
		put(result,ControlVariableConstant.NROW叶排数, arrayString[5]);
		put(result,ControlVariableConstant.M计算站数, arrayString[6]);
		put(result,ControlVariableConstant.N流线数, arrayString[7]);
		put(result,ControlVariableConstant.JSP汇总表起站, arrayString[8]);
		put(result,ControlVariableConstant.JFR终站, arrayString[9]);
		put(result,ControlVariableConstant.JSP汇总表起站, arrayString[10]);
		put(result,ControlVariableConstant.JFP终站, arrayString[11]);

		//KGP,WW,ICP,IDEV
		row++;
		arrayString = spliteRow(list.get(row),6,4);
		put(result,ControlVariableConstant.KGP小流管流量分布, arrayString[0]);
		put(result,ControlVariableConstant.WW转向, arrayString[1]);
		put(result,ControlVariableConstant.ICP变比热, arrayString[2]);
		put(result,ControlVariableConstant.IDEV输入叶型角, arrayString[3]);
		
		//#4:JS1,JF1,JS2,JF2 为处在叶排进出口边界的计算站序
		int nrow = getInteger(result,ControlVariableConstant.NROW叶排数[0]);
		List<String> line1 = new ArrayList<String>();
		result.put("ControlVariable.line1", line1);
		while(nrow>0){
			row++;
			int count = nrow*2;
			if(count>=12){
				count = 12;
				nrow -= 6;
			}else{
				count = nrow*2;
				nrow = 0;
			}
			arrayString = spliteRow(list.get(row),6,count);
			addAllToLine(line1,arrayString);
		}
		
		List<List<String>> grid1 = new ArrayList<List<String>>();
		List<String> aLine = null;
		//#5:H2(1:L1),(L1>2)
		row++;
		aLine = spliteRowByWidth(list.get(row),6,12);
		grid1.add(aLine);
		
		//#6:PIN(1:L1)
		row++;
		aLine = spliteRowByWidth(list.get(row),6,12);
		grid1.add(aLine);
		
		//#7:TIN(1:L1)
		row++;
		aLine = spliteRowByWidth(list.get(row),6,12);
		grid1.add(aLine);
		
		//#8:VUIN(1:L1)
		row++;
		aLine = spliteRowByWidth(list.get(row),6,12);
		grid1.add(aLine);
		
		//#9:HF0(1:L2)
		row++;
		aLine = spliteRowByWidth(list.get(row),6,12);
		grid1.add(aLine);
		
		//#10： SIG0(1:L2)-
		row++;
		aLine = spliteRowByWidth(list.get(row),6,12);
		grid1.add(aLine);
		result.put("ControlVariable.grid1", GridDataUtil.transform(grid1));
		
		
		//#11:MUH,MUM,MUT,SPV,FPV,IDF,PB:
		row++;
		arrayString = spliteRow(list.get(row),6,7);
		put(result,ControlVariableConstant.MUH根, arrayString[0]);
		put(result,ControlVariableConstant.MUM中, arrayString[1]);
		put(result,ControlVariableConstant.MUT尖, arrayString[2]);
		put(result,ControlVariableConstant.SPV超音解站范围起始站, arrayString[3]);
		put(result,ControlVariableConstant.FPV终止站, arrayString[4]);
		put(result,ControlVariableConstant.IDF分流尾站序, arrayString[5]);
		put(result,ControlVariableConstant.LPB计算站形状, arrayString[6]);
		
		//#12:KPV— 根部流路轮廓上的基准点的数目，3≤ KPV≤ 40,如KPV=0 则按计算站输入流路， 此时在程序中KPV=M。
		row++;
		put(result,ControlVariableConstant.KPV根部流路, list.get(row));
		
		int kpv = getInteger(result,ControlVariableConstant.KPV根部流路[0]);
		int kpvCount = kpv;
		if(kpv==0){
			kpvCount = getInteger(result,ControlVariableConstant.M计算站数[0]);
		}
		//logger.info(n);
		//#13-1,13-2… RHUB(1:KPV)一根部流路基准点(或计算站)的半径坐标， mm。
		int count = 0;
		List<List<String>> grid2 = new ArrayList<List<String>>();
		List<String> line = new ArrayList<String>();
		while(count<kpvCount){
			row++;
			List<String> cols = spliteRowByWidth(list.get(row),6);
			line.addAll(cols);
			count += cols.size();
		}
		grid2.add(line);
		//logger.info(line);

		//#14-1,14-2… ZHUB(1:KPV)— 根部流路基准点(或计算站)的轴向坐标， mm。
		count = 0;
		line = new ArrayList<String>();
		while(count<kpvCount){
			row++;
			List<String> cols = spliteRowByWidth(list.get(row),6);
			line.addAll(cols);
			count += cols.size();
		}
		grid2.add(line);
		//logger.info(line);
		
		if(kpv!=0){
			//#15-1,15-2… :KPV=0 不输,ZHUBS(1:M)— 根部流路沿Z 轴所有计算站的Z 坐标
			count = 0;
			kpvCount = getInteger(result,ControlVariableConstant.M计算站数[0]);
			line = new ArrayList<String>();
			while(count<kpvCount){
				row++;
				List<String> cols = spliteRowByWidth(list.get(row),6);
				line.addAll(cols);
				count += cols.size();
			}
			grid2.add(line);
		}
		
		//#16:KPP— 尖部流路轮廓上的基准点的数
		row++;
		put(result,ControlVariableConstant.KPP尖部流路, list.get(row));
		
		int kpp = getInteger(result,ControlVariableConstant.KPV根部流路[0]);
		int kppCount = kpp;
		if(kpp==0){
			kppCount = getInteger(result,ControlVariableConstant.M计算站数[0]);
		}
		
		//#17-1,17-2… RTIP(1:KPP)— 尖部流路基准点(或计算站)的半径坐标， mm。
		count = 0;
		line = new ArrayList<String>();
		while(count<kppCount){
			row++;
			List<String> cols = spliteRowByWidth(list.get(row),6);
			line.addAll(cols);
			count += cols.size();
		}
		grid2.add(line);
		//logger.info(line);
		
		//#18-1,18-2… ZTIP(1:KPP)— 尖部流路基准点(或计算站)的轴向坐标， mm。
		count = 0;
		line = new ArrayList<String>();
		while(count<kppCount){
			row++;
			List<String> cols = spliteRowByWidth(list.get(row),6);
			line.addAll(cols);
			count += cols.size();
		}
		grid2.add(line);
		//logger.info(line);
		
		if(kpp!=0){
			//#19-1,19-2… ：KPP=0 不输，ZTIPS(1:M)— 尖部流路沿Z 轴所有计算站的Z 坐标
			count = 0;
			kppCount = getInteger(result,ControlVariableConstant.M计算站数[0]);
			line = new ArrayList<String>();
			while(count<kppCount){
				row++;
				List<String> cols = spliteRowByWidth(list.get(row),6);
				line.addAll(cols);
				count += cols.size();
			}
			grid2.add(line);
		}
		
		//logger.info(grid1);
		
		/*
		当PB=0 时， 为直线计算站；
		当PB=1 时，为抛物线计算站，此时应输入抛物线中间限制点坐标。
		当PB=2 时， 为由输入的离散点定义的计算站， 各站均需输入。
		当PB=3 时，只输入叶排进出口站的离散点坐标
		*/
		String lpb = getString(result,ControlVariableConstant.LPB计算站形状[0]);
		if("1".equals(lpb)){
			//#20-1,20-2,… ：PB=1 时输,RMID(1:M)-各计算站叶中R 坐标，mm。
			int m = getInteger(result,ControlVariableConstant.M计算站数[0]);
			line = new ArrayList<String>();
			row = getDataByCount(row,list,line,m);
			grid2.add(line);
			//#21-1,21-2,… ：PB=1 时输,ZMID(1:M)-各计算站叶中Z 坐标，mm。
			line = new ArrayList<String>();
			row = getDataByCount(row,list,line,m);
			grid2.add(line);
		}else{
			Map<String,Object> scatterPointsMap = new HashMap<String,Object>();
			result.put("ControlVariable.scatterPointsMap", scatterPointsMap);
			int temp_count = 0;
			if("2".equals(lpb)){
				//#20-1,20-2… ： PB=2 时输。
				temp_count = getInteger(result,ControlVariableConstant.M计算站数[0]);
				//line = new ArrayList<String>();
				//row = getDataByCount(row,list,line,m);
			}else if("3".equals(lpb)){
				//#20-1,20-2… … ： PB=3 时输。
				temp_count = getInteger(result,ControlVariableConstant.NROW叶排数[0]);
				temp_count = temp_count*2;
				//line = new ArrayList<String>();
				//row = getDataByCount(row,list,line,m);
			}
			
			for(int i=0;i<temp_count;i++){
				Map<String,Object> map = new HashMap<String,Object>();
				List<String> lineTemp = (List<String>)result.get("ControlVariable.line1");
				List<List<String>> scatterPointsGrid = new ArrayList<List<String>>();
				scatterPointsMap.put(String.valueOf(i+1), map);
				if("2".equals(lpb)){
					map.put("pos", String.valueOf(i+1));
				}else if("3".equals(lpb)){
					map.put("pos", lineTemp.get(i));
				}
				//NLS-定义该计算站的离散点数
				row++;
				int nls = (int) Double.parseDouble(list.get(row));
				map.put("nls", String.valueOf(nls));
				//ZLS(1:NLS)-离散点Z 坐标， mm。
				line = new ArrayList<String>();
				row = getDataByCount(row,list,line,nls);
				scatterPointsGrid.add(line);
				//RLS(1:NLS)-离散点半径， mm。
				line = new ArrayList<String>();
				row = getDataByCount(row,list,line,nls);
				scatterPointsGrid.add(line);
				
				scatterPointsGrid = GridDataUtil.transform(scatterPointsGrid);
				map.put("grid", scatterPointsGrid);
			}
		}
		
		//#22-1， 22-2:KBK(1:M)-各计算站附面层堵塞因子， KBK>1.0
		int m = getInteger(result,ControlVariableConstant.M计算站数[0]);
		
		line = new ArrayList<String>();
		row = getDataByCount(row,list,line,m);
		grid2.add(line);
		grid2 = GridDataUtil.transform(grid2);
		//grid2 = GridDataUtil.trim(grid2, 0, 34);
		result.put("ControlVariable.grid2", grid2);
		
		nrow = getInteger(result,ControlVariableConstant.NROW叶排数[0]);
		Map<String,Object> nrowMap = new HashMap<String,Object>();
		result.put("ControlVariable.nrowMap", nrowMap);
		for(int i=0;i<nrow;i++){
			Map<String,Object> map = new HashMap<String,Object>();
			nrowMap.put(String.valueOf(i+1), map);
			//#R1:IZ,RMH,RMM,RMT,QFH,QFM,QFT,DIZ,GRL,GRT,DW,W
			row++;
			arrayString = spliteRow(list.get(row),6,12);
			put(map,ControlVariableConstant.IZ叶片数, arrayString[0]);
			put(map,ControlVariableConstant.RMH根, arrayString[1]);
			put(map,ControlVariableConstant.RMM中, arrayString[2]);
			put(map,ControlVariableConstant.RMT尖, arrayString[3]);
			put(map,ControlVariableConstant.QFH根, arrayString[4]);
			put(map,ControlVariableConstant.QFM中, arrayString[5]);
			put(map,ControlVariableConstant.QFT尖, arrayString[6]);
			put(map,ControlVariableConstant.DIZ多步计算叶片数增量, arrayString[7]);
			put(map,ControlVariableConstant.GRL绘图控制, arrayString[8]);
			put(map,ControlVariableConstant.GRT, arrayString[9]);
			put(map,ControlVariableConstant.DW角速度增量, arrayString[10]);
			put(map,ControlVariableConstant.W转动角速度, arrayString[11]);
			
			//#R2:PX,NL,KH,KG,TAU1,TAU2,TAU3,PB,PQ,QSH,QSM,QST
			row++;
			arrayString = spliteRow(list.get(row),6,12);
			put(map,ControlVariableConstant.PX叶片力计入控制, arrayString[0]);
			put(map,ControlVariableConstant.NL计算步数, arrayString[1]);
			put(map,ControlVariableConstant.KH增压系数, arrayString[2]);
			put(map,ControlVariableConstant.KG流量系数, arrayString[3]);
			put(map,ControlVariableConstant.TAU1前跟, arrayString[4]);
			put(map,ControlVariableConstant.TAU2轴中, arrayString[5]);
			put(map,ControlVariableConstant.TAU3尾尖, arrayString[6]);
			put(map,ControlVariableConstant.PB解题形式识, arrayString[7]);
			put(map,ControlVariableConstant.PQ功分布标识, arrayString[8]);
			put(map,ControlVariableConstant.QSH根, arrayString[9]);
			put(map,ControlVariableConstant.QSM中, arrayString[10]);
			put(map,ControlVariableConstant.QST尖, arrayString[11]);
			
			List<List<String>> grid3 = new ArrayList<List<String>>();
			//#R3:DTH(1:L3)-该叶排各站的叶片根部周向厚度， mm。
			row++;
			List<String> cols = spliteRowByWidth(list.get(row),6);
			grid3.add(cols);
			
			//#R4:DTM(1:L3)-该叶排各站的叶片叶中周向厚度， mm。
			row++;
			cols = spliteRowByWidth(list.get(row),6);
			grid3.add(cols);
			
			//#R5:DTT(1:L3)-该叶排各站的叶片尖部周向厚度， mm。
			row++;
			cols = spliteRowByWidth(list.get(row),6);
			grid3.add(cols);
	
			//#R6:HFR(1:L4)，(3≤ L4≤ 12)-在叶排出口边界上点在根尖流路点连线上的长度（ 由根算起） 坐标
			row++;
			cols = spliteRowByWidth(list.get(row),6);
			grid3.add(cols);
			
			
			//#R7:SIGS-在叶排出口边界HFR 点上的总压恢复系数值。
			row++;
			cols = spliteRowByWidth(list.get(row),6);
			grid3.add(cols);
			
			//#R8:VUS-在叶排出口边界HFR 点上的周向分速度值（ PB=0),m/s。
			row++;
			cols = spliteRowByWidth(list.get(row),6);
			grid3.add(cols);
			
			map.put("ControlVariable.grid3", GridDataUtil.transform(grid3));
			
			
			int nm = getInteger(result,ControlVariableConstant.NM最多步数[0]);
			if(nm!=0){
				//#R9:NM=0 不输,DSIG-在釆用连续多步计算时SIGS 的总增量。
				row++;
				put(map,ControlVariableConstant.DSIG多步计算总增量, list.get(row));
				
				//#R10:DVU,DALF,NM=0 不输，
				row++;
				arrayString = spliteRow(list.get(row),6,2);
				
			}

		}
		
		//#23:KGP=0 不输， GP(1:N)-每一流线下通过的流量与总流量之比。
		String kgp = getString(result,ControlVariableConstant.KGP小流管流量分布[0]);
		if(!"0".equals(kgp)){
			List<List<String>> grid4 = new ArrayList<List<String>>();
			int n = getInteger(result,ControlVariableConstant.N流线数[0]);
			line = new ArrayList<String>();
			row = getDataByCount(row,list,line,n);
			setGrid4(grid4,line);
			//grid4.add(line);
			//result.put("ControlVariable.grid4", GridDataUtil.transform(grid4));
			result.put("ControlVariable.grid4", grid4);
		}
		
		setGrid2(grid2,line1);
		
		return result;
	}
	
	public static void setGrid4(List<List<String>> grid4,List<String> line){
		for(int i=0;i<line.size();i++){
			List<String> row = new ArrayList<String>();
			row.add(String.valueOf(i+1));
			row.add(line.get(i));
			grid4.add(row);
		}
	}
	
	public static void setGrid2(List<List<String>> grid2,List<String> line1){
		boolean flag = true;
		for(int i=0;i<grid2.size();i++){
			List<String> row = grid2.get(i);
			boolean portFalg = false;
			for(String strPortNum:line1){
				int portNum = (int)Double.parseDouble(strPortNum);
				if(portNum==i+1){
					portFalg = true;
					break;
				}
			}
			
			String value = "";
			if(portFalg){
				value =flag?"0":"1";
				flag = !flag;
			}
			
			row.add(0, value);
		}
	}
	
	public static void addAllToLine(List<String> line,String[] arrayString){
		for(String str:arrayString){
			line.add(str);
		}
	}
	
	public static void addLineToGrid(List<List<String>> grid,String[] arrayString){
		List<String> line = new ArrayList<String>();
		for(String str:arrayString){
			line.add(str);
		}
		grid.add(line);
	}
	
	public static int getDataByCount(int startRow,List<String> list,List<String> result,int count){
		int row = startRow;
		int pos = 0;
		while(pos<count){
			row++;
			List<String> cols = spliteRowByWidth(list.get(row),6);
			result.addAll(cols);
			pos += cols.size();
		}
		return row;
	}
}
