package com.aerodynamic.design.domain.admin;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity 
public class RoleMenu {
	@Id 
    @Column(name="id")    
    @GeneratedValue
	private Long roleMenuId;
	
	@ManyToOne(cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "roleid",unique=false )
	private Role role;
	
	@ManyToOne(cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "menuid",unique=false )
	private Menu menu;
	
	public Long getRoleMenuId() {
		return roleMenuId;
	}

	public void setRoleMenuId(Long roleMenuId) {
		this.roleMenuId = roleMenuId;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

}
