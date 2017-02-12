package service;

import dao.AccountDao;
import domain.Account;
import impl.AccountDaoImpl;

public class AccountService {
	private AccountDao accountDao = new AccountDaoImpl();

	public Account getBalance(int accountId2) {
		 
		return accountDao.get(accountId2);
	}
	
	
}
