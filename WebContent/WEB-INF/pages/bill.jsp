<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery-3.1.1.min.js"></script>
<script type="text/javascript">
	$(function(){
		

	});
</script>
</head>
<body>
	<center>
		<h2>结账页面</h2>
		<br><br>
		<div>您购物车中共有本${sessionScope.shoppingCart.bookNum  } 书</div>
		<br><br>
		<div>应付${sessionScope.shoppingCart.totalMoney }钱</div>
		<br><br>
		<c:if test="${requestScope.errors != null }">
			<font color="red" >${requestScope.errors }</font>
		</c:if>
		<form action="bookServlet?method=check" method="post">
			<table>
				<tr>
					<td>姓名：<input type="text" name="userName"/></td>
				</tr>		
				<tr>
					<td>卡号：<input type="text" name="accountId"/></td>
				</tr>
				<tr><td><input type="submit" value="Submit"/></td></tr>
			</table>
			
		</form>
	</center>
</body>
</html>