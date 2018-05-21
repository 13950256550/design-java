package com.aerodynamic.design.domain;

public class InputItem {
	private String id;
	private String label;
	private String value;
	private boolean disabled = false;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public boolean isDisabled() {
		return disabled;
	}
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	public InputItem(String id, String label, String value) {
		this.id = id;
		this.label = label;
		this.value = value;
		this.disabled = false;
	}
	
	public InputItem(String id, String label, String value, boolean disabled) {
		this.id = id;
		this.label = label;
		this.value = value;
		this.disabled = disabled;
	}
}
