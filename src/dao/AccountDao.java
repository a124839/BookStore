package dao;

import domain.Account;

public interface AccountDao {
	/**����accountId��ȡ��Ӧ�� Account����
	 * @param accouontId
	 * @return
	 */
	public abstract Account get(Integer accouontId);
	/**���ݴ����accountId��amount�����˻����۳�amountָ����Ǯ��
	 * @param accountId
	 * @param amount
	 */
	public abstract void updateBalance(Integer accountId, float amount);
}
