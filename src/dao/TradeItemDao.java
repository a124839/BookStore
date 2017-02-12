package dao;

import java.util.Collection;
import java.util.Set;

import domain.TradeItem;

public interface TradeItemDao {
	/**批量保存 Trade Item对象
	 * @param items
	 */
	public abstract void batchSave(Collection<TradeItem> items);
	/**根据 trade Id 获取和其关联的 Trade Item 集合
	 * @param tradeId
	 * @return
	 */
	public abstract Set<TradeItem> geTradeItemsWithTradeId(Integer tradeId);
}
