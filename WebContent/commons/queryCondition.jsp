<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">

	//加了return false这个function在好用？为什么

	$(function(){
		$("a").each(function(){
			this.onclick = function(){
				var serializeVal = $(":hidden").serialize();
				var href = this.href + "&" + serializeVal;
				window.location.href = href;
				return false;
			};
		});
	});
</script>

	<input type="hidden" name="minPrice" value="${param.minPrice }"/>
	<input type="hidden" name="maxPrice" value="${param.maxPrice }"/>