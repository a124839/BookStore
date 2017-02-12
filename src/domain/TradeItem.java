package domain;

public class TradeItem {
	//��tradeItem������ Book
	private Book book;
	private int quantity;
	private Integer bookId;
	//��TradeItem������bookId
	private Integer tradeId;
	public TradeItem() {
		super();
	}
	public TradeItem(Book book, int quantity, Integer bookId, Integer tradeId) {
		super();
		this.book = book;
		this.quantity = quantity;
		this.bookId = bookId;
		this.tradeId = tradeId;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Integer getBookId() {
		return bookId;
	}
	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}
	public Integer getTradeId() {
		return tradeId;
	}
	public void setTradeId(Integer tradeId) {
		this.tradeId = tradeId;
	}
	@Override
	public String toString() {
		return "TradeItem [book=" + book + ", quantity=" + quantity + ", bookId=" + bookId + ", tradeId=" + tradeId
				+ "]";
	}
	
	
	
}
