<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"  /> 
<%request.setCharacterEncoding("UTF-8");%>      
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 정보 수정창</title>
<style>
.cls1{
	font-size: 40px;
	text-align:center;
}
</style>
</head>
<body>
<h1 class="cls1">회원 정보 수정창</h1> <!--수정하기 클릭시 컨트롤러에 /member/modMember.do로 요청 -->
<form method="post" action="${contextPath }/member/modMember.do?id=${memInfo.id}">
	<table align="center">
		<tr>
		
	</table>
</form>
</body>
</html>