package com.aerodynamic.design.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.aerodynamic.design.domain.admin.Menu;
import com.aerodynamic.design.domain.admin.User;
import com.aerodynamic.design.repository.MenuRepository;
import com.aerodynamic.design.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService{
	@Resource
    private MenuRepository menuRepository;
	
	@Override
	public List<Menu> getMenus(User user) {
		List<Menu> menus = menuRepository.getSubMenu();
		return menus;
	}

	@Override
	public List<Menu> getChildren(Menu parent) {
		List<Menu> menus = menuRepository.getChildren(parent);
		return menus;
	}

}
