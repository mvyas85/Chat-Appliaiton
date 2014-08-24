<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Chat Box9</title>
	<link rel="stylesheet" type="text/css" href="css/loginStyle.css">
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	<script>
	var string2 = '<%= session.getAttribute("loginUser").toString() %>';
	eval('var onlineUser='+string2);
	
	
	var webSocket = new WebSocket("ws://localhost:8080/manishaChatApplication/sendMessage");
      	
       	webSocket.onopen = function() {
       	webSocket.send(onlineUser.name);
	  };
	  
	  webSocket.onerror = function(err) {
	      alert("Error: " + err);
	  };
	  webSocket.onmessage = function (message) {
		  
		  if(message.data == "new_user_loggedin"){
			  loadOnlineUsers();
			  return;
		  }
		  var jsonData = JSON.parse(message.data);
		  
		  if(jsonData.message != null)
		  {
			  var replysg = jsonData.senderName+": "+jsonData.message;
			  if($("#"+jsonData.senderName).find("#chatHistory").val() != null){
			  }
			  else
			  {
				  startChat(jsonData.senderName);
			  }
			  $("#"+jsonData.senderName).show();
			  var oldChatHistory = $("#"+jsonData.senderName).find("#chatHistory").val();
			  $("#"+jsonData.senderName).find("#chatHistory").val(oldChatHistory +replysg);
		  }
	  };

	  webSocket.onclose = function(message) {

		  alert("Exiting Chat Server!");
		};	  
				  
		function loadOnlineUsers(event){
			$("#boxTextDiv").load("OnlineUser");
		}
		
		$(document).ready(function(){
			$("#boxTextDiv").load("OnlineUser");
			$("#textSubmit").click(loadOnlineUsers);
			$('#boxTextDiv').dblclick(startChat);
		});
		
		function startChat(userName1) {
			var userName;
			if(userName1.length > 0){
				userName = userName1;
				//alert("userName1:"+userName);
			}
			else
			{
				userName = $("#boxTextDiv option:selected").text();
				//alert("userName:"+userName);

			}
			
			
			if( $( "#"+userName).length ){
				if(! $( "#"+userName).is(":visible")  ){
					$( "#"+userName).show();
				}
				return;
			}
			
			//create chat window where id base div is user name.
			var chatWindow = "<div id='"+userName+"' class='chatDiv'>"+
			"<div class='chatDivTitle'>"+userName+" <a href='#' style='float:right' class='chatClose'>close</a></div>"+
				"<textarea rows='12' id='chatHistory' class='chatHistory' readonly='true' cols='30' ></textarea><br>"+
				"<textarea rows='3' id='chatBox' class='chatBox' cols='30' ></textarea>"+
			"</div>";
			$(".allChatDivs").append(chatWindow);
		    
			 //event on close button for newly created chat window
			$("#"+userName).find(".chatClose").click(function(event){
				$(this).closest(".chatDiv").hide();
			});
			 
			//set event keyup on chat box text area of this chat window
			$("#"+userName).find("#chatBox").keyup(function(event){
				
				 var keycode = (event.keyCode ? event.keyCode : event.which);
				if(keycode == '13'){
					var message=$(this).val();
						
				 var senderMsg = new Object();
						 senderMsg.message = message;
						 senderMsg.senderId  =onlineUser.id;
						 senderMsg.senderName = onlineUser.name;
						 senderMsg.sendTo = userName;
						 
				   var jsonMsgString= JSON.stringify(senderMsg);
						   
						   
					var oldChatHistory = $("#"+userName).find("#chatHistory").val();
					$("#"+userName).find("#chatHistory").val(oldChatHistory +"me: "+message);
					
					//alert(jsonMsgString);
					webSocket.send(jsonMsgString);
					
					$(this).val('');
					
					//set scroller down chatHistory text area when somthing typed and appended to the history
					var $charHistory = $("#"+userName).find("#chatHistory");
					$charHistory .scrollTop($charHistory.prop("scrollHeight"));

			}
				//if esc pressed in the chat box then hide the chat window 
				if (event.keyCode == '27') {
					$(this).closest(".chatDiv").hide();
				} 
			
			});
		};
		
	</script> 
</head>
	<body>
	<a href="${pageContext.request.contextPath}/logoutprocess" style="font-size:25px;float:right">Logout</a>
	
	<div id="center">
	<h2>Welcome ${sessionScope['loginUser'].name} </h2><br/> login time is 
	<%= new java.util.Date() %>
	
		<h2>Click on user to start chat</h2>
		
		<div class="onlineUsersDIV" >
			<h2 ID="header">Online users</h2>
			
			<select size="10" class="onlineuserlist"  id="boxTextDiv">
			</select>
			
			<form><input id="textSubmit" class="login-submit" type="button" value="Refresh Online Users"/></form>
		</div>
	</div>
	<div class="allChatDivs">	
	</div>
	</body>
</html>
