package com.aerodynamic.design.domain.admin;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long userId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "avatar")
	private String avatar;
	
	@Column(name="passwd")
	@JsonIgnore
	private String passwd;
	
	@Column(name = "path")
	private String path;
	
	@Transient
	private long loginDateTime;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public long getLoginDateTime() {
		return loginDateTime;
	}

	public void setLoginDateTime(long loginDateTime) {
		this.loginDateTime = loginDateTime;
	}
	
	public long duration(){
		return new Date().getTime()-this.loginDateTime;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", duration=" + duration() + "]";
	}

}
