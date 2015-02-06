package com.epam.cdp.jmp.jms.banksystem.dto;


public class Role {
	private long id;

	private String name;

	public Role() {
		this.id = 2;
		this.name = "customer";
	}

	public Role(String roleName) {
		this.name = roleName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
