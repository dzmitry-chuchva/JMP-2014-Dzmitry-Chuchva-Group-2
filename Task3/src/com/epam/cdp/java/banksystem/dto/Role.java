package com.epam.cdp.java.banksystem.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class Role {
	@Id
	@Column(name = "role_id")
	private long id;

	@Column(name = "role")
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
