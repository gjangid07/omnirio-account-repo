package com.omnirio.Account.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity(name = "ROLE")
public class Role {

	@Id
	@GeneratedValue
	@Column(name = "Role_Id")
	private int roleId;

	@Column(name = "Role_Number")
	private String roleName;

	@Column(name = "Role_Code")
	private RoleCode roleCode;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "User_Id", nullable = false)
	private User user;

//	@Transient
	private int usrId;

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public RoleCode getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(RoleCode roleCode) {
		this.roleCode = roleCode;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getUsrId() {
		return usrId;
	}

	public void setUsrId(int usrId) {
		this.usrId = usrId;
	}

}
