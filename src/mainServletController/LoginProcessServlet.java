package mainServletController;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.AsyncContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.ChatDbOperations;
import exceptions.ChatDbFailure;
import model.User;

@WebServlet("/loginprocess")
public class LoginProcessServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

    public LoginProcessServlet() {
        super();
    }

	public void init(ServletConfig config) throws ServletException {
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ServletContext context = request.getServletContext();
		HttpSession session = request.getSession(true);
//		if(session != null){
//			session.invalidate();
//		}
//		session = request.getSession(true);
		RequestDispatcher dispatch =null;
		User loginUser=null;
		
		User user = new User(request);
		String tName = user.getName();
		String tPass = user.getPass();
		
		////////////// If one of the username/password field is empty//////////

		if (tName == null || tPass == null) {
			System.out.println("There is an error returning back!");
			/* The form contained invalid data, transfer colontrol back to original form */
			
			request.setAttribute("user", user);
			if(tName == null){
				request.setAttribute("errorInNameMsg", "Name can not be null !");}
			if(tPass == null){
				request.setAttribute("errorInPassMsg", "Password can't be null !");}
			dispatch = context.getRequestDispatcher("/login.jsp");
		} 
		///////Otherwise try to login//////
		else{
		
			try {
				loginUser = ChatDbOperations.loginToAccount(tName,tPass);
			} catch (ChatDbFailure | SQLException e) {
				e.printStackTrace();
			}
			//////If Database do not have any match found then login user/Password incorrect////
			if(loginUser==null){
				request.setAttribute("loginFailMsg", "Login Failed !");
				dispatch = context.getRequestDispatcher("/login.jsp");
			 }
			else{ //////////Else success!! //////
				session = request.getSession();
				session.setAttribute("loginUser", loginUser);
				
				dispatch = context.getRequestDispatcher("/WEB-INF/chatMainPage.jsp");
			}
		}
		
		
		dispatch.forward(request, response);
	}

	
}
