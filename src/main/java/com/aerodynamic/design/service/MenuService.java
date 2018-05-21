package com.aerodynamic.design.service;

import java.util.List;

import com.aerodynamic.design.domain.admin.Menu;
import com.aerodynamic.design.domain.admin.User;

public interface MenuService {
	public List<Menu> getMenus(User user);
	
	public List<Menu> getChildren(Menu parent);
}
