package com.aerodynamic.design.domain.admin;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
public class Menu {
	@Id
	@GeneratedValue
	@Column(name = "id")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long menuId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "icon")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String icon;
	
	@Column(name = "path")
	private String path;
	
	@ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH},fetch=FetchType.EAGER)
	@JoinColumn(name = "parentid",unique=false )
	@JsonIgnore
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Menu parent;
	
	@OneToMany(mappedBy="parent",cascade = CascadeType.ALL,orphanRemoval = true)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<Menu> children;

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getPath() {
		if(parent!=null){
			return parent.getPath()+"/"+path;
		}else{
			return "/"+path;
		}
		
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Menu getParent() {
		return parent;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}

	public List<Menu> getChildren() {
		if(children==null){
			
		}
		return children;
	}

	public void setChildren(List<Menu> children) {
		this.children = children;
	}

	public Menu(String name, String icon, String path) {
		this.name = name;
		this.icon = icon;
		this.path = path;
	}
	
	public Menu() {
	}
	
}
