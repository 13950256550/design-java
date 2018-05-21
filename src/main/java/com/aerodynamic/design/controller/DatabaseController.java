package com.aerodynamic.design.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aerodynamic.design.domain.admin.Menu;
import com.aerodynamic.design.repository.MenuRepository;
import com.aerodynamic.design.repository.UserRepository;
import com.aerodynamic.design.service.MenuService;

@Controller
@RequestMapping("/api")
public class DatabaseController {
	@Resource
    private UserRepository userRepository;
	
	@Resource
    private MenuRepository menuRepository;
	
	@Resource
	MenuService menuService;
	
	@RequestMapping(path = "/init")
	@ResponseBody
	@Transactional
	public Map insertData(){
		Map map = new HashMap();
		Menu subMenu = new Menu("一维设计","form","1d_design");
		List<Menu> children = new ArrayList<Menu>(); 
		
		Menu item = new Menu("输入",null,"input");
		item.setParent(subMenu);
		children.add(item);
		item = new Menu("输出",null,"output");
		item.setParent(subMenu);
		children.add(item);

		subMenu.setChildren(children);
		
		menuRepository.save(subMenu);
		return map;
	}
	
	@RequestMapping(path = "/query")
	@ResponseBody
	public Map queryData(){
		Map map = new HashMap();
		List<Menu> menus = menuService.getMenus(null);
		map.put("data", menus);
		return map;
	}
}
