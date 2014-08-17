<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Create User</title>
		<link rel="stylesheet" type="text/css" href="../css/create_login_page.css">
	</head>
	
	<body>
			<div class="center"><img alt="lets get social" /></div>
		   <div class="login-card">
		   
			    <h1>Create Account</h1><br>
			  <form action="${pageContext.request.contextPath}/jsp/createUserProcess" class="login"  method="POST">
			  			<p><font size="3" color="red">${errorInNameMsg}</font></p>
			    <input type="text" name="name" placeholder="Username" value="${user.name}" autofocus>
			   			<p><font size="3" color="red">${errorInPassMsg}</font> </p>
			    <input type="password" name="password" placeholder="Password" value="${user.pass}">
			    
			    <input type="submit" name="login" class="login login-submit" value="Create Account">
			  </form>
			  
			</div>
			<a href="../login.jsp">Go Home</a>
	</body>
</html>