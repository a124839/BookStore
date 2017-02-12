package domain;

/**封装购物车对象，包含商品以及包含该商品的数量
 * @author k570
 *
 */
public class ShoppingCartItem {
	private Book book;
	private int quantity;
	
	public ShoppingCartItem() {
		super();
	}
	
	public ShoppingCartItem(Book book, int quantity) {
		super();
		this.book = book;
		this.quantity = quantity;
	}

	public ShoppingCartItem(Book book) {
		this.book = book;
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
	
	/**
	 * @return 该商品在购物车中的钱数
	 */
	public float getItemMoney() {
		
		return book.getPrice()*quantity;
	}
	
	/**
	 * 增加单个数量
	 */
	public void increatment(){
		quantity++;
	}
}
