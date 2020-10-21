package com.omnirio.Account.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.omnirio.Account.model.Account;
import com.omnirio.Account.repository.AccountRepository;

@Service
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;

	public List<Account> getAllAccounts() {
		List<Account> accounts = accountRepository.findAll();

//		for (Account account : accounts) {
//			account.getUser().getRole().setUsrId(account.getUser().getUserId());
//			account.getUser().getRole().setUser(null);
//		}
		return accounts;
	}

	public Optional<Account> findById(Integer id) throws Exception {
		Optional<Account> account = accountRepository.findById(id);

		if (!account.isPresent())
			throw new Exception("account not found with id-" + id);

//		Account accnt = account.get();
//		accnt.getUser().getRole().setUsrId(accnt.getUser().getUserId());
//		accnt.getUser().getRole().setUser(null);
		return account;
	}

	public Account insertAccount(Account User) {
		return accountRepository.save(User);
	}

	public Account updatingAccount(Account account) {
		return accountRepository.save(account);
	}

	public void deleteAccountById(int id) {
		accountRepository.deleteById(id);

	}
}
