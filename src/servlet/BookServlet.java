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


/**下面的dopost方法中的代码不太明白
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
		//1.空字符串校验。如果不为空则进行用户名和账号的校验
		if (errors.toString().equals("")) {
			errors = vaildUser(userName, accountIdStr);
			//2.用户名个账号匹配校验。如果匹配则进行商品的库存校验。
			if (errors.toString().equals("")) {
				errors = validStoreNum(request);
				System.out.println(errors.toString());
				//3.库存校验。通过，进行总钱数与付款数校验，如果通过则结账
				if (errors.toString().equals("")) {
					errors = validBalance(accountIdStr, request);
					System.out.println("============" + errors.toString() + "============");
					//4.账户余额校验，通过则结账
					if (errors.toString().equals("")) {
						flag = true;
					}
				}
			}
		}
		//4.事务。结账，对用户账户中钱数修改。
			//验证未通过
		if (!flag) {
			request.setAttribute("errors", errors);
			request.getRequestDispatcher("/WEB-INF/pages/bill.jsp").forward(request, response);
		}else {
			//验证通过，结账
			bookService.bill(sc, userName, accountIdStr);
			response.sendRedirect(request.getContextPath() + "/success.jsp");
		}
		
	
	}
	
	/**账户余额校验
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
			errors.append("您账户余额不足以付清全部钱款");
		}
		return errors;
	}
	
	/**商品的库存校验
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
				errors.append(sci.getBook().getTitle() + "库存不足！");
			}
		}
		return errors;
	}
	
	/**用户名个账号匹配校验
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
			errors.append("用户名个账户不匹配，请重新输入");
			
		}
		
		return errors;
	}
	
	/**简单校验，验证表单是否符合规范，是否为空，是否可以转成int类型，是否是下一个email。。不需要经过数据库或调用任何业务的方法
	 * @param userName
	 * @param accountId
	 * @return erros信息
	 */
	public StringBuffer validForm(String userName, String accountId) {
		StringBuffer errors = new StringBuffer("");
		
		if (userName == null || userName.trim().equals("")) {
			errors.append("用户名不能为空<br>");
			
		}
		if (accountId == null || accountId.trim().equals("")) {
			errors.append("账号不能为空<br>");
		}
		return errors;
	}
		
	protected void forwordPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String page = request.getParameter("page");
		request.getRequestDispatcher("/WEB-INF/pages/" + page + ".jsp").forward(request, response);
	}
	protected void updateItemQuantity(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//4.在updateItemQuantity 方法中，获取quantity，id，再获取购物车对象，调用service方法修改
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
		
		//5.传回json数据：bookNum：xx，totalMoney
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
		
		System.out.println("===============赋值了么===================");
		
		//传入的过程中有可能出现异常，
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
		
		//1.获得商品id
		String idStr = request.getParameter("id");
		int id = -1;
		boolean flag = false;
		try {
			id = Integer.parseInt(idStr);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		//需要判断id是否合法
		if (id > 0) {
			//2.获得购物车对象
			ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
			
			//3.调用book Service的addToCart方法把商品放到购物车中去
			//问题：addToCart方法为什么是boolean类型
			flag = bookService.addToCart(id,sc);
			
		}
		if (flag) {
			//4.直接调用getBooks方法
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
		
		//有可能查询的book不存在
		if (book == null) {
			response.sendRedirect(request.getContextPath() + "/error-1.jsp");//重定向到错误页面
			return;//后面不用再执行
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
