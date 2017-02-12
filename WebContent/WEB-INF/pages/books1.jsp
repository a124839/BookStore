<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery-3.1.1.min.js"></script>
<%@ include file="/commons/queryCondition.jsp" %>

<script type="text/javascript">
	$(function(){
		
		$("#pageNo").change(function(){

			var val = $(this).val();//获得id为pageNO的值
			val = $.trim(val);
			
			//1.校验val是否为数字？
			var reg = /^\d+$/g;//正则表达式，需要具体研究
			if(!reg.test(val)){
				alert("输入值不合法");
				$(this).val("");
				return;
			}
			//2.val是否在合法范围内？
			var pageNo = parseInt(val);//因为页面上的获取的是字符串所以需要把字符串转换为int。这里的方法为js本身的方法
			
			if(pageNo < 1 || pageNo > parseInt("${bookPage.totalPageNum }")){
				alert("超出总页数");
				$(this).val("");
				return;
			}
			//3.页面跳转
			var href = "bookServlet?method=getBooks&pageNo=" + pageNo +"&" + $(":hidden").serialize();//带查询条件
			window.location.href = href;
			});
	});
</script>

</head>
<body>
	<center>
		<c:if test="${param.title != null}">
			您已经将${param.title}放入购物车
		</c:if>
		<br><br>
		<c:if test="${!empty sessionScope.shoppingCart.books }">
			您购物车中有${sessionScope.shoppingCart.bookNum  } 件商品 &nbsp;&nbsp;<a href="bookServlet?method=forwordPage&page=cartPage&pageNo=${bookPage.pageNo }">查看购物车</a>		
		</c:if>
	
		<br><br>
		<form action="bookServlet?method=getBooks" method="post">
			price:
			<input type="text" name="minPrice" size="1"/> -
			<input type="text" name="maxPrice" size="1"/>
			<input type="submit" value="Submit"/>
		</form>
		<br><br>
		<table cellpadding="10">
			<c:forEach items="${bookPage.list }" var="book">
				<tr>
				<td><a href="bookServlet?method=getBook&id=${book.id }&pageNo=${bookPage.pageNo } ">${book.title }</a>
				<br>${book.author }</td>
				<td>${book.price }</td>
				<td><a href="bookServlet?method=addToCart&id=${book.id }&pageNo=${bookPage.pageNo }&title=${book.title}">加入购物车</a></td>
				</tr>
				<br>
			</c:forEach>
		</table>
		<br><br>
		
		共${bookPage.totalPageNum }页&nbsp; &nbsp;当前第${bookPage.pageNo } 页&nbsp;&nbsp;
		
		<c:if test="${bookPage.hasPrev }">
			<a href="bookServlet?method=getBooks&pageNo=1">首页</a>
			&nbsp;&nbsp;
			<a href="bookServlet?method=getBooks&pageNo=${bookPage.prevPage }">上一页</a>
		</c:if>
		&nbsp;&nbsp;
		
		<c:if test="${bookPage.hasNext }">
			<a href="bookServlet?method=getBooks&pageNo=${bookPage.nextPage }">下一页</a>
			&nbsp;&nbsp;
			<a href="bookServlet?method=getBooks&pageNo=${bookPage.totalPageNum }">末页</a>
		</c:if>
		&nbsp;&nbsp;
	
	
		转到<input type="text" id="pageNo" size="2" /> 页
	</center>
</body>
</html>