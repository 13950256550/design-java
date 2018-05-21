package com.aerodynamic.design.service;

import com.aerodynamic.design.domain.admin.Menu;
import com.aerodynamic.design.domain.admin.User;

public interface UserService {
	public User queryUserByName(String name);
	public Iterable<Menu> getMenusByUser(User user);
}
