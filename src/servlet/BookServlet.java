package servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;	
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import domain.Account;
import domain.Book;
import domain.ShoppingCart;
import domain.ShoppingCartItem;
import domain.User;
import service.AccountService;
import service.BookService;
import service.UserService;
import utils.BookStoreWebUtils;
import web.CriteriaBook;
import web.Page;


/**�����dopost�����еĴ��벻̫����
 * @author k570
 *
 */
@WebServlet("/BookServlet")
public class BookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public BookServlet() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	private BookService bookService = new BookService();
	private AccountService accountService = new AccountService();
	private UserService userService = new UserService();
	protected void check(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter("userName");
		String accountIdStr = request.getParameter("accountId");
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		boolean flag = false;
		StringBuffer errors = new StringBuffer("");
		errors = validForm(userName, accountIdStr);
		//1.���ַ���У�顣�����Ϊ��������û������˺ŵ�У��
		if (errors.toString().equals("")) {
			errors = vaildUser(userName, accountIdStr);
			//2.�û������˺�ƥ��У�顣���ƥ���������Ʒ�Ŀ��У�顣
			if (errors.toString().equals("")) {
				errors = validStoreNum(request);
				System.out.println(errors.toString());
				//3.���У�顣ͨ����������Ǯ���븶����У�飬���ͨ�������
				if (errors.toString().equals("")) {
					errors = validBalance(accountIdStr, request);
					System.out.println("============" + errors.toString() + "============");
					//4.�˻����У�飬ͨ�������
					if (errors.toString().equals("")) {
						flag = true;
					}
				}
			}
		}
		//4.���񡣽��ˣ����û��˻���Ǯ���޸ġ�
			//��֤δͨ��
		if (!flag) {
			request.setAttribute("errors", errors);
			request.getRequestDispatcher("/WEB-INF/pages/bill.jsp").forward(request, response);
		}else {
			//��֤ͨ��������
			bookService.bill(sc, userName, accountIdStr);
			response.sendRedirect(request.getContextPath() + "/success.jsp");
		}
		
	
	}
	
	/**�˻����У��
	 * @param accountId
	 * @param request
	 * @return errors
	 */
	public StringBuffer validBalance(String accountId, HttpServletRequest request) {
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		StringBuffer errors = new StringBuffer("");
		int accountId2 = Integer.parseInt(accountId);
		Account account = accountService.getBalance(accountId2);
		float balance = account.getBalance();
		float totalMoney = sc.getTotalMoney();
//		boolean flag = false;
		if (totalMoney > balance) {
			errors.append("���˻������Ը���ȫ��Ǯ��");
		}
		return errors;
	}
	
	/**��Ʒ�Ŀ��У��
	 * @param request
	 * @return errors
	 */
	public StringBuffer validStoreNum(HttpServletRequest request) {
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
//		ShoppingCartItem sci = sc.getItems();
		StringBuffer errors = new StringBuffer("");
		
		for (ShoppingCartItem sci : sc.getItems()) {
//			int storeNum = sci.getBook().getStoreNumber();
			int storeNum = bookService.getBook(sci.getBook().getId()).getStoreNumber();
			int quantity = sci.getQuantity();
			if (quantity > storeNum) {
				errors.append(sci.getBook().getTitle() + "��治�㣡");
			}
		}
		return errors;
	}
	
	/**�û������˺�ƥ��У��
	 * @param userName
	 * @param accountId
	 * @return errors
	 */
	public StringBuffer vaildUser(String userName, String accountId) {
		StringBuffer errors = new StringBuffer("");
		User user = userService.getUserByUserName(userName);
		boolean flag = false;
		if (user != null) {
			if (Integer.parseInt(accountId) == user.getAccountId()) {
				flag = true;	
			};
		}
		if (!flag) {
			errors.append("�û������˻���ƥ�䣬����������");
			
		}
		
		return errors;
	}
	
	/**��У�飬��֤���Ƿ���Ϲ淶���Ƿ�Ϊ�գ��Ƿ����ת��int���ͣ��Ƿ�����һ��email��������Ҫ�������ݿ������κ�ҵ��ķ���
	 * @param userName
	 * @param accountId
	 * @return erros��Ϣ
	 */
	public StringBuffer validForm(String userName, String accountId) {
		StringBuffer errors = new StringBuffer("");
		
		if (userName == null || userName.trim().equals("")) {
			errors.append("�û�������Ϊ��<br>");
			
		}
		if (accountId == null || accountId.trim().equals("")) {
			errors.append("�˺Ų���Ϊ��<br>");
		}
		return errors;
	}
		
	protected void forwordPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String page = request.getParameter("page");
		request.getRequestDispatcher("/WEB-INF/pages/" + page + ".jsp").forward(request, response);
	}
	protected void updateItemQuantity(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//4.��updateItemQuantity �����У���ȡquantity��id���ٻ�ȡ���ﳵ���󣬵���service�����޸�
		String idStr = request.getParameter("id");
		String quantityStr = request.getParameter("quantity");
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		int id = -1;
		int quantity = -1;
		try {
			id = Integer.parseInt(idStr);
			quantity = Integer.parseInt(quantityStr);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		if (id > 0 && quantity > 0) {
			
			bookService.updateItemQuantity(id,quantity,sc);
		}
		
		//5.����json���ݣ�bookNum��xx��totalMoney
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("bookNum", sc.getBookNum());
		result.put("totalMoney", sc.getTotalMoney());
		
		Gson gson= new Gson();
		String jsonStr = gson.toJson(result);
		response.setContentType("text/javascript");
		response.getWriter().print(jsonStr);
		
		
	}
	
	protected void clear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		bookService.clear(sc);
		request.getRequestDispatcher("/WEB-INF/pages/emptyCart.jsp").forward(request, response);
	}
	
	protected void removeItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("id");
		int id = -1;
		
		try {
			id = Integer.parseInt(idStr);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		bookService.removeItem(id,sc);
		
		if (sc.isEmpty()) {
			request.getRequestDispatcher("/WEB-INF/pages/emptyCart.jsp").forward(request, response);
			return;
		}
//		toCartPage(request, response);
		request.getRequestDispatcher("/WEB-INF/pages/cartPage.jsp").forward(request, response);
		
	}
	
	protected void toCartPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/pages/cartPage.jsp").forward(request, response);
		
	}
	
	public void getBooks(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		String pageNoStr = request.getParameter("pageNo");
		String minPriceStr = request.getParameter("minPrice");
		String maxPriceStr = request.getParameter("maxPrice");
		
		System.out.println(pageNoStr + "==" + minPriceStr + "==" +maxPriceStr );
		
		int pageNo = 1 ;
		int minPrice = 0;
		int maxPrice = Integer.MAX_VALUE;
		
		System.out.println("===============��ֵ��ô===================");
		
		//����Ĺ������п��ܳ����쳣��
		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		try {
			minPrice = Integer.parseInt(minPriceStr);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		try {	
			maxPrice = Integer.parseInt(maxPriceStr);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		System.out.println(pageNoStr + "^^^" + minPriceStr + "^^^" +maxPriceStr );
		
		CriteriaBook criteriaBook = new CriteriaBook(minPrice, maxPrice, pageNo);
		Page<Book>	page = bookService.getPage(criteriaBook);
		request.setAttribute("bookPage", page);
		request.getRequestDispatcher("/WEB-INF/pages/books1.jsp").forward(request, response);
	}
	
	public void addToCart(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		
		//1.�����Ʒid
		String idStr = request.getParameter("id");
		int id = -1;
		boolean flag = false;
		try {
			id = Integer.parseInt(idStr);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		//��Ҫ�ж�id�Ƿ�Ϸ�
		if (id > 0) {
			//2.��ù��ﳵ����
			ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
			
			//3.����book Service��addToCart��������Ʒ�ŵ����ﳵ��ȥ
			//���⣺addToCart����Ϊʲô��boolean����
			flag = bookService.addToCart(id,sc);
			
		}
		if (flag) {
			//4.ֱ�ӵ���getBooks����
			getBooks(request, response);
			return;
		}
		response.sendRedirect(request.getContextPath() + "/error-1.jsp");
		
	}
	public void getBook(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		String idStr = request.getParameter("id");
		int id = -1;
		Book book = null;
		try {
			id = Integer.parseInt(idStr);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		if (id > 0) 
			book = bookService.getBook(id);
		
		//�п��ܲ�ѯ��book������
		if (book == null) {
			response.sendRedirect(request.getContextPath() + "/error-1.jsp");//�ض��򵽴���ҳ��
			return;//���治����ִ��
		}
		request.setAttribute("book", book);
		request.getRequestDispatcher("/WEB-INF/pages/book.jsp").forward(request, response);
		
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String methodName = request.getParameter("method");
		Method method;
		try {
			method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			method.setAccessible(true);
			method.invoke(this, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} 
				
		
	}

}
