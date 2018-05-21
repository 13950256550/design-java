package com.aerodynamic.design.domain.admin;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity 
public class UserRole {
	@Id 
    @Column(name="id")    
    @GeneratedValue
	private Long userRoleId;
	
	@ManyToOne(cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "userid",unique=false )
	private User user;
	
	@ManyToOne(cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "roleid",unique=false )
	private Role role;

	public Long getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(Long userRoleId) {
		this.userRoleId = userRoleId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
}
