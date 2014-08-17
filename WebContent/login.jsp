<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Insert title here</title>
		<link rel="stylesheet" type="text/css" href="css/style.css">
		<script>
			/* function replaceTextInBox(event){
				$("#boxTextDiv").load("OnlineUser");
			} */
			$(document).ready(function(){
				$(".login-submit").click("OnlineUser");
			});
		</script>
	</head>
	
	<body >
	<a href="${pageContext.request.contextPath}/jsp/createUser.jsp">Create User</a>
		  <form action="${pageContext.request.contextPath}/loginprocess" class="login" style="float:left;" method="POST">
		    <h1>Login</h1>
		    <p><font size="3" color="red">${loginFailMsg}</font></p>
				 <p><font size="3" color="red">${errorInNameMsg}</font></p>
		    <input type="TEXT" name="name" class="login-input" placeholder="User Name" value="${user.name}" autofocus>
		     				<p><font size="3" color="red">${errorInPassMsg}</font> </p>
		    <input type="password" name="password" class="login-input" placeholder="Password" value="${user.pass}">
		    				 
		    <input type="submit" value="Login" class="login-submit">
		  </form>
		  
	</body>
</html>