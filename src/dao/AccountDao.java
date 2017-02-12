package dao;

import domain.Account;

public interface AccountDao {
	/**根据accountId获取对应的 Account对象
	 * @param accouontId
	 * @return
	 */
	public abstract Account get(Integer accouontId);
	/**根据传入的accountId，amount更新账户余额，扣除amount指定的钱数
	 * @param accountId
	 * @param amount
	 */
	public abstract void updateBalance(Integer accountId, float amount);
}
