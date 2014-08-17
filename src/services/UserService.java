package services;

import java.sql.SQLException;

import model.User;
import db.ChatDbOperations;

public class UserService {

	private static User newUser;
	

	public static void createUser(User aNewUser) 
	{
		newUser = aNewUser;
		
		//Insert a new row in the a table:: 
		System.out.println("Adding New User :: " + newUser);

		try {
			ChatDbOperations.insertUser(newUser);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
