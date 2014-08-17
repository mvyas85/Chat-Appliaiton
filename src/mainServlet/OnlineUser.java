package mainServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User;
import db.ChatDbOperations;
import exceptions.ChatDbFailure;

@WebServlet("/OnlineUser")
public class OnlineUser extends HttpServlet implements Observer {
	//private static final long serialVersionUID = 1L;
    
	private static final long serialVersionUID = -1066736333925199622L;
	public OnlineUser() {
        super();
    }

	public void init(ServletConfig config) throws ServletException {
	}
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		//currentUser = (User)session.getAttribute("loginUser");
		
		List<User> onlineUsers = null;
		
		try {
			onlineUsers = ChatDbOperations.printChatUsers(User.ONLINE);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		session.setAttribute("listOfLoggedinUsers", onlineUsers);
		 out.println("");
		  for(int i=0;i<onlineUsers.size();i++){
			 System.out.println(onlineUsers.get(i).getName());
			  out.println("<option class='useroption'   value='"+ onlineUsers.get(i).getName() +"'>"
					  		+ onlineUsers.get(i).getName() 
					  		+ "<div></div></option><br>");
		  }
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Inside doget method of timeservlet");
		
	}
	@Override
	public void update(Observable o, Object arg) {
		System.out.println("Trying to update values");
	}
}
