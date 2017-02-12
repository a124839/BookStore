package dao;

import java.util.Collection;
import java.util.Set;

import domain.TradeItem;

public interface TradeItemDao {
	/**�������� Trade Item����
	 * @param items
	 */
	public abstract void batchSave(Collection<TradeItem> items);
	/**���� trade Id ��ȡ��������� Trade Item ����
	 * @param tradeId
	 * @return
	 */
	public abstract Set<TradeItem> geTradeItemsWithTradeId(Integer tradeId);
}
