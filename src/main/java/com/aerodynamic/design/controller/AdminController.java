package com.aerodynamic.design.controller;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aerodynamic.design.domain.admin.Menu;
import com.aerodynamic.design.domain.admin.User;
import com.aerodynamic.design.service.MenuService;
import com.aerodynamic.design.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	private static Map<String,Object> CONTEXT = new HashMap<String,Object>();
	public static String workPath = "E:/17-06-07/EXE";
	
	@Resource
	MenuService menuService;
	
	@Resource
	UserService userService;
	
	@RequestMapping("/currentUser/{sessionid}")
	@ResponseBody
    public Map currentUser(@PathVariable String sessionid){
		Map map = new HashMap();
    	User user = (User)CONTEXT.get(sessionid); 

    	map.put("data", user);
    	logger.info("currentUser{}",map);
    	return map;
    }
	
	@RequestMapping("/menus/{sessionid}")
	@ResponseBody
    public Iterable<Menu> menus(@PathVariable String sessionid){
		/*
		List<Menu> menus = new ArrayList<Menu>();
		List<Menu> children = new ArrayList<Menu>(); 
		Menu item = new Menu("403",null,"403");
		children.add(item);
		item = new Menu("404",null,"404");
		children.add(item);
		item = new Menu("500",null,"500");
		children.add(item);
		Menu subMenu = new Menu("异常页","warning","exception");
		subMenu.setChildren(children);
		
		menus.add(subMenu);
		*/
		User user = (User)CONTEXT.get(sessionid); 
		Iterable<Menu> menus = userService.getMenusByUser(user);
		
		logger.info("menus{}",menus);
    	return menus;  
    }
	
	@PostMapping("/login/account")
	@ResponseBody
    public Map login(@RequestBody Map<String,String> map){
		logger.info("login{}",map);
		UUID uuid = UUID.randomUUID();
		String sessionid = uuid.toString();
		/*
		User user = new User();
		user.setName(map.get("userName"));
		user.setPasswd(map.get("password"));
		user.setAvatar("headimg.png");
		*/
		User user = userService.queryUserByName(map.get("userName"));
		user.setLoginDateTime(new Date().getTime());
		
		//List<Menu> menus = menuService.getMenus(user);
		
		CONTEXT.put(sessionid, user);
		Map result = new HashMap();
		
		result.put("status", "ok");
		result.put("type", "account");
		result.put("currentAuthority", "user");
		result.put("sessionid", sessionid);
		//result.put("user", user);
		//result.put("menus", menus);
    	return result;  
    }
	
	@RequestMapping("/logout/{sessionid}")
	@ResponseBody
    public Map logout(@PathVariable String sessionid){
		logger.info("logout {}",sessionid);
		Map result = new HashMap();
		CONTEXT.remove(sessionid);
		
		result.put("status", "ok");
    	return result;  
    }
	
	@RequestMapping("/calculate/{sessionid}")
	@ResponseBody
    public Map calculate(@PathVariable String sessionid){
		logger.info("calculate {}",sessionid);
		String path = AdminController.getWorkPath(sessionid);
		String lineStr = null;
		Map result = new HashMap();
		Runtime run = Runtime.getRuntime();
		String cmd = path+"/1D/1D_RUN.EXE";
		StringBuilder sb = new StringBuilder();
		try {
			Process p = run.exec(cmd);// 启动另一个进程来执行命令 
			BufferedInputStream in = new BufferedInputStream(p.getInputStream());  
			BufferedReader inBr = new BufferedReader(new InputStreamReader(in));  
			//String lineStr;  
			while ((lineStr = inBr.readLine()) != null) { 
			    //获得命令执行后在控制台的输出信息  
				sb.append(lineStr);
				sb.append("\n");
			}
			//检查命令是否执行失败。  
			if (p.waitFor() != 0) { 
				//p.exitValue()==0表示正常结束，1：非正常结束  
			    if (p.exitValue() == 1){
			    	sb.append("\n\n命令执行失败!\n");
			    }else if (p.exitValue() == 0){
			    	sb.append("\n\n命令执行完成!\n");
			    }
			}  
			inBr.close();  
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		result.put("data", sb.toString());
    	return result;  
    }
	
	public static void checkContext(){
		Set<String> keys = CONTEXT.keySet();
		for(String key:keys){
			Object object = CONTEXT.get(key);
			if(object instanceof User){
				long duration = ((User)object).duration();
				if(duration>2*60*60*1000){
					CONTEXT.remove(key);
				}
			}
		}
		logger.info("checkContext  CONTEXT{}",CONTEXT);
	}
	
	public static String getWorkPath(String sessionid){
		String path = AdminController.workPath;
		User user = (User)CONTEXT.get(sessionid); 
		if(user!=null){
			path = user.getPath();
		}
		return path;
	}
}
