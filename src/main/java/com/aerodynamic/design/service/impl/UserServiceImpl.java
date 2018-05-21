package com.aerodynamic.design.service.impl;

import javax.annotation.Resource;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.aerodynamic.design.domain.admin.Menu;
import com.aerodynamic.design.domain.admin.Role;
import com.aerodynamic.design.domain.admin.User;
import com.aerodynamic.design.repository.MenuRepository;
import com.aerodynamic.design.repository.UserRepository;
import com.aerodynamic.design.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	@Resource
    private UserRepository userRepository;
	
	@Resource
    private MenuRepository menuRepository;

	@Override
	public User queryUserByName(String name) {
		return userRepository.findByName(name);
	}

	@Override
	public Iterable<Menu> getMenusByUser(User user) {
		Iterable<Role> roles = userRepository.findRolesByUser(user);
		Iterable<Menu> menus = menuRepository.getMenusByRoles(roles);
		return menus;
	}
}
