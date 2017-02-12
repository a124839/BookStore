<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd" >

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>Insert title here</title>
<script type="text/javascript" src="../script/jquery-3.1.1.min.js"></script>
<script type="text/javascript">
	$(function(){
		$("a").click(function(){
			var serializeVal = $(":hidden").serialize();
			var href = this.href + "&" +serializeVal;
			window.location.href = href;
			
			return false;
			});

		
		});
	
</script>
</head>
<body>

	<input type="hidden" name="minPrice" value="${param.minPrice }"/>
	<input type="hidden" name="maxPrice" value="${param.maxPrice }"/>

	<center>
		<br>
		<p>${bookPage.pageNo }&nbsp;</p>
		<br>
		<form action="bookServlet?method=getBooks" method="post">
			price: 
			<input type="text" size="1" name="minPrice" /> -
			<input type="text" size="1" name="maxPrice" /> 
			<input type="submit" value="Submit" />	
		</form>

		<br>
		<br>
		
		<table cellpadding="10">
			<c:forEach items="${bookPage.list}" var="book">
				<tr>
					<td><a href="bookServlet?getBooks&pageNo=${bookPage.pageNo}&id=${book.id}">${book.title}</a> 
					<br> ${book.author}</td>
					<td>${book.price}</td>
					<td><a href="">加入购物车</a></td>
				</tr>
				<br>
			</c:forEach>
		</table>
		
		
		
		<br>
		<br>
		<!-- 得到总页数，下面的el表达式中totalItemNum看page类中的getTotalPageNum方法名 -->
		共 ${bookPage.totalPageNum } 页 &nbsp;&nbsp; 当前第 ${bookPage.pageNo} 页
		&nbsp;&nbsp;
		<!-- 注意逻辑：首先判断是否有前一页，有前一页则执行。-->
		<!-- 问题1：获得的链接为那个类的什么方法返回的什么结果  -->
		<!-- 回答1：在servlet 类中把book类封装成的page信息命名为了bookPage这一方法为getbook，使用这一方法完成得到下一页 -->
		<!-- 问题2：hasPrev这个貌似没有出现过啊？怎么判断是否有下一页的呢？难道是隐藏的对象？ -->
		<!-- 回答2：hasPrev是page类中的isHasPrev方法去掉了is，不过不清楚为什么去掉is-->
		<!-- 问题3：与上个问题类似，prevPage貌似也没有在已经写好的类中出现过，它真的存在并且有效么？ -->
		
		
		<%-- <c:if test="${bookPage.hasPrev }">
			<a href="bookServlet?method=getBooks&pageNo=1">首页</a>
			&nbsp;&nbsp;
			<a href="bookServlet?method=getBooks&pageNo=${bookPage.prevPage }">上一页</a>
		</c:if>
			&nbsp;&nbsp; --%>
			
			
		<c:if test="${bookPage.pageNo > 0 }">
			<a href="bookServlet?method=getBooks&pageNo=1">首页</a>
			&nbsp;&nbsp;
			<a href="bookServlet?method=getBooks&pageNo=${bookPage.prevPage }">上一页</a>		
		</c:if>
			&nbsp;&nbsp;
			

		<%-- <c:if test="${boookPage.hasNext }">
			<a href="bookServlet?method=getBooks&pageNo=${bookPage.nextPage }">下一页</a>
			&nbsp;&nbsp;
			<a href="bookServlet?method=getBooks&pageNo=${bookPage.totalPageNum }">末页</a>
		</c:if> --%>
		
		<c:if test="${bookPage.pageNo < bookPage.totalPageNum }">
		
			<a href="bookServlet?method=getBooks&pageNo=${bookPage.nextPage}">下一页</a>
			&nbsp;&nbsp;
			<a href="bookServlet?method=getBooks&pageNo=${bookPage.totalPageNum }">末页</a>
		
		</c:if>
			&nbsp;&nbsp;
			
			
		转到 <input type="text" size="1" id="pageNo" /> 页
	</center>
</body>
</html>