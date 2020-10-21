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

@Entity(name = "ACCOUNT")
public class Account {

	@Id
	@GeneratedValue
	@Column(name = "Account_Id")
	private int accountID;

	@Column(name = "Account_Type")
	private AccountType accountType;

	@Temporal(value = TemporalType.DATE)
	@Column(name = "Account_Open_Date")
	private Date accountOpenDate;

	@OneToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "User_Id", nullable = false)
	private User user;

	@Column(name = "Branch")
	private String branch;

	@Column(name = "IsMinor")
	private boolean isMinor;

	@Column(name = "CustomerId")
	private int customerId;

	@Column(name = "customerName")
	private String customerName;

	public int getAccountID() {
		return accountID;
	}

	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public Date getAccountOpenDate() {
		return accountOpenDate;
	}

	public void setAccountOpenDate(Date accountOpenDate) {
		this.accountOpenDate = accountOpenDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public boolean isMinor() {
		return isMinor;
	}

	public void setMinor(boolean isMinor) {
		this.isMinor = isMinor;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

}
