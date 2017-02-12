package dao;

import java.util.Set;

import domain.Trade;

public interface TradeDao {
	/**向数据表中插入 Trade 对象
	 * @param trade
	 */
	public abstract void insert(Trade trade);
	/**根据 user Id 获取和其关联的 Trade 集合
	 * @param userId
	 * @return
	 */
	public abstract Set<Trade> geTradesWithUserId(Integer userId);
}
