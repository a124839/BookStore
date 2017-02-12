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
		$(".delete").click(function(){
			//获得当前的。td。tr
			var $tr = $(this).parent().parent();
			//再find第一个td。的文本值
			var title = $.trim($tr.find("td:first").text());
			var deleteFlag = confirm("确定要删除" + title + "的信息么");

			if(deleteFlag){
				return true;
			}
			//取消默认行为
			return false;
		});
		
		//ajax修改单个商品数量
		//1.获取页面中的所有text，并为其添加onchange响应函数
		$(":text").change(function(){
			var quantityVal = $.trim(this.value); 
			var flag = false;		

			var reg = /^\d+$/g;
			var quantity = -1;
			if(reg.test(quantityVal)){
				quantity = parseInt(quantityVal);
				if(quantity >= 0){
					flag = true;
				}
			}
			//对输入的数量进行校验
			if(!flag){
				alert("输入的数量不合法");
				$(this).val($(this).attr("class"));
				return;
			}

			var $tr = $(this).parent().parent();
			var title = $.trim($tr.find("td:first").text());

			if(quantity == 0){
				var flag2 = confirm("确定要删除" + title + "么");
				if(flag2){
				//得到a节点
				var $a = $tr.find("td:last").find("a");
				$a[0].onclick();

				return;
				}
			}
			
			var flag3 = confirm("确定要修改" + title +"的数量么？");
			if(!flag){
				$(this).val($(this).attr("class"));
				return;
			}
		//2.请求地址为：bookServlet
			var url= "bookServlet";
		//3.请求参数为：method：updateItemQuantity，id：name属性值，quantity：val，time：new Date（）
		//这里涉及到页面中的这些属性的获取方法，需要好好看看
			var idVal = $.trim(this.name);
			var args = {"method":"updateItemQuantity","id":idVal,"quantity":quantityVal,"time":new Date};
		//4.在updateItemQuantity 方法中，获取quantity，id，再获取购物车对象，调用service方法修改
		//5.传回json数据：bookNum：xx，totalMoney
		//6.更新当前页面的bookNum和totalMoney
			$.post(url,args,function(data){
				var bookNum = data.bookNum;
				var totalMoney = data.totalMoney;

				$("#bookNum").text("您的购物车中有" + bookNum + "本书");
				$("#totalMoney").text("总金额：￥" + totalMoney);
			},"JSON");
		});
	});
</script>
</head>
<body>
	<center>
		<h1>查看购物车</h1>
		<br><br>
		<div id="bookNum">
			您的购物车中有${sessionScope.shoppingCart.bookNum }本书
		</div>
		<br><br>
		
		<table cellpadding="10">
			
			<tr>
				<td>书名</td>
				<td>数量</td>
				<td>单价</td>
				<td>&nbsp;</td>
			</tr>
			
			<c:forEach items="${sessionScope.shoppingCart.items}" var="item">
				<tr>
					<td>${item.book.title }</td>
					<td>
						<input type="text" size="2" class="${item.quantity }" name="${item.book.id }" value="${item.quantity }"/>
					</td>
					<td>${item.book.price }</td>
					<td><a href="bookServlet?method=removeItem&pageNo=${param.pageNo}&id=${item.book.id }" class="delete">删除</a></td>
				</tr>
			</c:forEach>
			<tr>
				<td colspan="4" id="totalMoney">总金额：￥${sessionScope.shoppingCart.totalMoney }</td>			
			</tr>
		</table>
		<br><br>
		<a href="bookServlet?method=getBooks&pageNo=${param.pageNo}">继续购物</a>
		<a href="bookServlet?method=clear&pageNo=${param.pageNo}">清空购物车</a>
		<a href="bookServlet?method=forwordPage&page=bill">结账</a>
	</center>
</body>
</html>