<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<center>
	<br><br>
	<h2>用户名：${user.userName }</h2>
		<table cellpadding="10">
			<c:forEach items="${user.trades }" var="trades">
				
				<tr>
				<td>
				<table cellpadding="10" border="1" cellspacing="0">
					<tr>
						<td colspan="3">交易时间；${trades.tradeTime }</td>
					</tr>
				
					<c:forEach items="${trades.tradeItems }" var="items">
						<tr>
							<td>${items.book.title }</td>
							<td>${items.book.price }</td>
							<td>${items.quantity }</td>
						</tr>
					</c:forEach>
					
				</table>
				<br><br>
				</td>
				</tr>
			</c:forEach>
		</table>
	</center>
</body>
</html>