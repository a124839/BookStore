package domain;

/**��װ���ﳵ���󣬰�����Ʒ�Լ���������Ʒ������
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
	 * @return ����Ʒ�ڹ��ﳵ�е�Ǯ��
	 */
	public float getItemMoney() {
		
		return book.getPrice()*quantity;
	}
	
	/**
	 * ���ӵ�������
	 */
	public void increatment(){
		quantity++;
	}
}
