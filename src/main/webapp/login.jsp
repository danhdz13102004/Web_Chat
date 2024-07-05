<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<form method="post" action="${pageContext.request.contextPath}/user/dang-nhap">
		<input placeholder="Username" type="text" name="username">
		<br>
		<input placeholder="Password" type="text" name="password">
		<button type="submit">Submit</button>
	</form>

</body>
</html>