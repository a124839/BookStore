package impl;

import dao.AccountDao;
import domain.Account;

public class AccountDaoImpl extends BaseDao<Account> implements AccountDao{

	
	@Override
	public Account get(Integer accouontId) {
		String sql = "SELECT accountId, balance FROM account WHERE accountId = ?";
		return query(sql, accouontId);
	}

	@Override
	public void updateBalance(Integer accountId, float amount) {
		String sql = "UPDATE account SET balance = balance - ? WHERE accountId = ?";
		update(sql, amount, accountId );
	}

}
