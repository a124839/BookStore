package domain;

import java.sql.Date;
import java.util.LinkedHashSet;
import java.util.Set;

public class Trade {
	//Trade对象对应的 id
	private Integer tradeId;
	//交易时间
	private Date tradeTime;
	//Trade关联的多个TradeItem
	private Set<TradeItem> tradeItems = new LinkedHashSet<TradeItem>();
	//Trade关联的uerId
	private Integer userId;
	
	public Trade() {
		super();
	}
	public Trade(Integer tradeId, Date tradeTime, Set<TradeItem> tradeItems, Integer userId) {
		super();
		this.tradeId = tradeId;
		this.tradeTime = tradeTime;
		this.tradeItems = tradeItems;
		this.userId = userId;
	}
	public Integer getTradeId() {
		return tradeId;
	}
	public void setTradeId(Integer tradeId) {
		this.tradeId = tradeId;
	}
	public Date getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(Date tradeTime) {
		this.tradeTime = tradeTime;
	}
	public Set<TradeItem> getTradeItems() {
		return tradeItems;
	}
	public void setTradeItems(Set<TradeItem> tradeItems) {
		this.tradeItems = tradeItems;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "Trade [tradeId=" + tradeId + ", tradeTime=" + tradeTime + ", tradeItems=" + tradeItems + ", userId="
				+ userId + "]";
	}
	
	
	
}
