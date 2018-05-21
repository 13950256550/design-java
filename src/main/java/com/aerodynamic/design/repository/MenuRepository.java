package com.aerodynamic.design.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.aerodynamic.design.domain.admin.Menu;
import com.aerodynamic.design.domain.admin.Role;

public interface MenuRepository extends CrudRepository<Menu,Long>{
	@Query("select menu from Menu menu where menu.parent=:parent ")
	public List<Menu> getChildren(@Param("parent") Menu parent);
	
	@Query("select menu from Menu menu where menu.parent is null ")
	public List<Menu> getSubMenu();
	
	@Query("select menu from Menu as menu,RoleMenu as roleMenu where roleMenu.menu=menu and roleMenu.role in (:roles) and menu.parent is null")
	public Iterable<Menu> getMenusByRoles(@Param("roles") Iterable<Role> roles);
}
