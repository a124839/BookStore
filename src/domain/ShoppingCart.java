package domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**���ﳵ��
 * @author k570
 *
 */
public class ShoppingCart {
	
	private  Map<Integer, ShoppingCartItem> books = new HashMap<>();
	
	/**���ﳵ��Ʒ��ӷ���
	 * @param book 
	 */
	public void add(Book book) {
		//����Ҫ�ж��Ƿ��й��ﳵ��װ����Ʒ����û�оʹ���һ������book����ŵ����ﳵ��Ʒ������
		//�������Ʒ���ﳵ������Ѷ�Ӧ��book����+1
		
		ShoppingCartItem sci = books.get(book.getId());
		
		if (sci == null) {
			sci = new ShoppingCartItem(book);
			books.put(book.getId(), sci);
		} else {
			sci.increatment();
		}
		
	}
	
	/**�ж��Ƿ��д��鼮����,ֱ�ӵ���map�еķ���������id�ж��Ƿ��д˶���
	 * @param id
	 * @return 
	 */
	public boolean hasBook(Integer id) {
		return books.containsKey(id);
	}
	
	/**���� Map<Integer, ShoppingCartItem>���͵� books����
	 * @return books
	 */
	public Map<Integer, ShoppingCartItem> getBooks() {		
		
		return books;
	}
	
	/**����Collection<ShoppingCartItem> �Ķ�Ӧcollection����
	 * @return 
	 */
	public Collection<ShoppingCartItem> getItems() {
		return books.values();
	}
	
	/**�����鼮����������foreachѭ����ֹ����ΪMap���͵Ķ����Ӧ��Collection����
	 * @return ����������
	 */
	public int getBookNum() {
		int total = 0;
		for (ShoppingCartItem sci : books.values()) {
			total += sci.getQuantity();
			
		}
		
		return total;
	}
	
	/**
	 * @return ������Ǯ��
	 */
	public float getTotalMoney() {
		int total = 0 ;
		
		for (ShoppingCartItem sci : getItems()) {
			total += sci.getItemMoney();
		}
		
		return total;
	}
	
	/**map���������ж��Ƿ�Ϊ�յķ�����ֱ�ӵ���
	 * @return
	 */
	public boolean isEmpty() {
		return books.isEmpty();
	}
	
	/**
	 * ��չ��ﳵ
	 */
	public void clear() {
		books.clear();
	}
	
	/**ɾ����Ʒ
	 * @param id
	 */
	public void removeItem(Integer id) {
		books.remove(id);
	}
	
	/**�޸Ĺ��ﳵ����Ʒ������
	 * @param id
	 * @param quantity
	 */
	public void updateItemQuantity(Integer id,int quantity) {
		ShoppingCartItem sci = books.get(id);
		if (sci != null) {
			sci.setQuantity(quantity);
		}
	}
}
