<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="test.css">
<title>Student Registration LGN001</title>
</head>
<body class="login-page-body">

	<div class="login-page">
		<div class="form">
			<div class="login">
				<div class="login-header">
					<h1>Welcome!</h1>
					<p>Please check your data again.</p>
				</div>
			</div>
			<div style="color: blue;">${msg }</div>

			<form class="login-form" action="LoginServlet" method="post"
				name="confirm">
				<input type="text" placeholder="User Id" name="uid" value="${data.uid }" />
				<input type="password" placeholder="Password" name="upwd" value="" />
				<input type="submit" value="Login">
				<p class="message">
					Not registered? <a href="USR001.jsp">Create an account</a>
				</p>
			</form>
		</div>
	</div>
</body>

</html>