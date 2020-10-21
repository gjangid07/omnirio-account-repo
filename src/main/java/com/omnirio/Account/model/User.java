package com.omnirio.Account.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Entity(name = "USER")
public class User {

	@Id
	@GeneratedValue
	@Column(name = "User_Id")
	private int userId;

	@Column(name = "User_Name")
	private String userName;

	@Temporal(value = TemporalType.DATE)
	@Column(name = "User_DOB")
	private Date dob;

	@Column(name = "User_Gender")
	private Gender gender;

	@Column(name = "User_PhoneNo")
	@Size(max = 10)
//	@Pattern(regexp = "(\\+91|0)[0-9]{9}")
	private String phoneNo;

//	@Column(name = "User_RoleId")
//	private int roleId;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
	public Role role;

	@OneToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "Account_Id", nullable = false)
	private Account account;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
