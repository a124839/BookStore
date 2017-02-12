package test;

import static org.junit.Assert.*;

import org.junit.Test;

import dao.AccountDao;
import domain.Account;
import impl.AccountDaoImpl;

public class TestAccouontDaoImpl {
	
	private AccountDao accountDao = new AccountDaoImpl();
	@Test
	public void test() {
		Account account = accountDao.get(1);
		System.out.println(account);
//		accountDao.updateBalance(1, 20);
//		System.out.println(account);
	}

}
