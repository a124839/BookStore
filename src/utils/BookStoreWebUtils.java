package utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import domain.ShoppingCart;

public class BookStoreWebUtils {
	
	
	
	/**获取购物车对象，从session中获取，若session中没有此对象，则在session中创建一个购物车对象，放入session中
	 * 若有直接返回
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
