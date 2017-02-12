package servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Trade;
import domain.User;
import service.BookService;
import service.UserService;

/**
 * Servlet implementation class User
 */
@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UserService userService = new UserService();
    
    public UserServlet() {
        super();
    }
    
//    protected void getTradeLog(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		String userName = request.getParameter("userName");
//		User user  = userService.getUserByUserName(userName);
//		if (user ==null ) {
//			response.sendRedirect(request.getContextPath() + "/error-1.jsp");
//		}
//		request.setAttribute("users", user);
//		request.getRequestDispatcher("/WEB-INF/pages/trade.jsp").forward(request, response);
//	}
    
	
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		doPost(request, response);
//	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		String methodName = request.getParameter("method");
//		Method method;
//		try {
//			method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
//			method.setAccessible(true);
//			method.invoke(this, request, response);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		}
		
		String userName = request.getParameter("userName");
		User user = userService.getUserByTrades(userName);
		if (user == null) {
			response.sendRedirect("/error-1.jsp");
			return;
		}
		request.setAttribute("user", user);
		request.getRequestDispatcher("/WEB-INF/pages/trade.jsp").forward(request, response);
		
	}

}
