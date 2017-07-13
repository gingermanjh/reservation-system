<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Task1</title>
</head>
<body>
	<form method="post" action="/categorys">
		name : <input type="text" name="name"> <input type="submit"
			value="추가">
	</form>
	<br>
	<br>
	<br>

	<table class="table" style="width: 100%">
		<tr>
			<th>id</th>
			<th>name</th>
			<th>update</th>
			<th>remove</th>
		</tr>
			<c:forEach var="i" items="${listInfo}">
				<tr>
					<th>${i.id}</th>
					<th>${i.name}</th>
					<th><form method="post" action="/categorys">
						<input type="hidden" name="_method" value="put">
							<input type="hidden" name="id" value="${i.id}"> <input
								type="text" name="name"> <input type="submit"
								value="update">
						</form></th>
					<th>
						<button class="delete" type="button" id="${i.id}">remove</button>
					</th>
				</tr>
			</c:forEach>
	</table>
	<br>

	<script src="/resources/node_modules/jquery/dist/jquery.js"></script>
	<script src="/resources/categorys.js"></script>
</body>
</html>