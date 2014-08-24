package mainServletController;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.UserService;
import model.User;

@WebServlet("/createUserProcess")
public class CreateUserProcessServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CreateUserProcessServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("Do Post");
		ServletContext context;
		context = getServletContext();
		
		User user = new User(request);
		RequestDispatcher dispatch;
		System.out.println("name is "+ user.getName());
		
		if (user.getName() == null || user.getPass() == null) {
			System.out.println("There is an error returning back!");
			/* The form contained invalid data, transfer control back to original form */
			
			request.setAttribute("user", user);
			if(user.getName() == null){
				request.setAttribute("errorInNameMsg", "Name can not be null !");}
			if(user.getPass() == null){
				request.setAttribute("errorInPassMsg", "Password can't be null !");}
			dispatch = context.getRequestDispatcher("/WEB-INF/createUser.jsp");
			dispatch.forward(request, response);
			return;
		} 
		
		System.out.println("Trying to create user");
		UserService.createUser(user);
		System.out.println("User Created Sccuess");
		
		dispatch = context.getRequestDispatcher("/WEB-INF/createUserSucess.jsp");
		dispatch.forward(request, response);
		
	}

}
