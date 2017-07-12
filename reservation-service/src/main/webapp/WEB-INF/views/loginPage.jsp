<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<c:if test="${sessionScope.loginInfo != null}">
	<c:redirect url="/check"/>
</c:if>

<c:if test="${sessionScope.loginInfo == null}">
	<form method="post" action="/login">
		아이디: <input type="text" name="name">
		비밀번호: <input
			type="password" name="passwd"> <input type="submit"
			value="로그인">
	</form>
</c:if>
</body>
</html>