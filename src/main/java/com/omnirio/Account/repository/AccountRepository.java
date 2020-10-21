package com.omnirio.Account.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.omnirio.Account.model.Account;


public interface AccountRepository extends JpaRepository<Account, Integer> {
	
	public Optional<Account> findById(Integer id);

}
