package utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import domain.ShoppingCart;

public class BookStoreWebUtils {
	
	
	
	/**��ȡ���ﳵ���󣬴�session�л�ȡ����session��û�д˶�������session�д���һ�����ﳵ���󣬷���session��
	 * ����ֱ�ӷ���
	 * @param request
	 * @return
	 */
	public static ShoppingCart getShoppingCart(HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		ShoppingCart sc = (ShoppingCart) session.getAttribute("shoppingCart");
		
		if (sc == null) {
			sc = new ShoppingCart();
			session.setAttribute("shoppingCart", sc);
		}
		return sc;
	}
}
