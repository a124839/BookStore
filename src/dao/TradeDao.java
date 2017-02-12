package dao;

import java.util.Set;

import domain.Trade;

public interface TradeDao {
	/**�����ݱ��в��� Trade ����
	 * @param trade
	 */
	public abstract void insert(Trade trade);
	/**���� user Id ��ȡ��������� Trade ����
	 * @param userId
	 * @return
	 */
	public abstract Set<Trade> geTradesWithUserId(Integer userId);
}
