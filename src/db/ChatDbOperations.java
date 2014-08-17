package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Message;
import model.Receipt;
import model.User;
import exceptions.ChatDbFailure;


public class ChatDbOperations {

	/**
	 * Insert into User Table 
	 * @param status - online/offline
	 * @throws SQLException  When unable to connect to DB or failed Query
	 */
	public static void insertUser(User user) throws SQLException{
		Connection dbConn;
		System.out.println("Trying to create dbConn");
		dbConn = ChatAppDataSource.getConnection();
		
		// Turn off auto-commit of db changes as they occur
		dbConn.setAutoCommit(false);
		
		String insertStmt = "INSERT INTO `manisha_chat_project`.`user`  (`name`, `pass`)  " +
				"VALUES (? , ?);";
				
		PreparedStatement queryStmt ;
		queryStmt = dbConn.prepareStatement(insertStmt);
		queryStmt.setString(1, user.getName()); 
		queryStmt.setString(2,user.getPass());
		
			
		try {
			queryStmt.executeUpdate();
			dbConn.commit(); // make changes permanent
			dbConn.close();
		} catch (SQLException ex) {
			dbErrorRollBackTx(dbConn); // call this function to give error and ROLLBACK
		}
	}

	/**
	 * Checking to see if User Name and Password are correct.
	 * If correct it change user status to online
	 * @param status - change to online
	 * @throws SQLException  When unable to connect to DB or failed Query
	 * 			or when invalid password
	 */
	public static User loginToAccount(String userName, String password) throws ChatDbFailure,SQLException {
		Connection dbConn;
		int rowsAffected;
		String queryStr = "SELECT * " + "FROM user "
				+ "WHERE name = '" + userName + "' and pass = '" + password + "'";
		
		dbConn = ChatAppDataSource.getConnection();
		Statement queryStmt = dbConn.createStatement();
		ResultSet results;
		
		User loggedInUser= new User();
		int userid;
		
		results = queryStmt.executeQuery(queryStr);
		
		if(results.last()){
			rowsAffected =  results.getRow();
		} else {
    	   rowsAffected = 0; //just cus I like to always do some kinda else statement.
		}
		
		if (rowsAffected != 1) {
			throw  new ChatDbFailure(ChatDbFailure.INVALID_CREDENTIAL);
		} else {
			userid = results.getInt("userid");
			
			loggedInUser.setId(userid);
			loggedInUser.setName(userName);
			loggedInUser.setPass(password);
			
			changeUserStatus(userid,User.ONLINE);
			
			loggedInUser.setStatus(User.ONLINE);
			
			// Free resources
			results.close();
			queryStmt.close();
			dbConn.close();
			System.out.println("welcome " +userName+" Login Sucessful !! ");
			return loggedInUser;
		}
		
	}

	/**
	 * Updating Users with status to offline
	 * @param userid - userId
	 * @throws SQLException  When unable to connect to DB or failed Query
	 */
	public static void logOutUser(int userid) throws SQLException, ChatDbFailure {
		changeUserStatus(userid, User.OFFLINE);
		System.out.println("Logout Sucessfull !! ");
	}
	
	/**
	 * Updating Users with status to offline
	 * @param userid - userId
	 * @throws SQLException  When unable to connect to DB or failed Query
	 */
	public static void logOutUserWithName(String name) throws SQLException, ChatDbFailure {
		changeUserStatus(name, User.OFFLINE);
		System.out.println("Logout Sucessfull !! ");
	}
	
	/**
	 * Updating Users with status
	 * @param status - online/offline
	 * @throws SQLException  When unable to connect to DB or failed Query
	 */
	public static void changeUserStatus(int userid, String newStatus)throws ChatDbFailure,SQLException {
		Connection dbConn;
		int rowsAffected;
		String queryStr = "UPDATE user SET status = ? where userid = ?;";
		
		dbConn = ChatAppDataSource.getConnection();
		
		// Turn off auto-commit so we can use transactions
        dbConn.setAutoCommit(false);

		// Update the balance 
		try (PreparedStatement updateCurBalStmt = dbConn.prepareStatement(queryStr)) {
			updateCurBalStmt.setString(1, newStatus);
			updateCurBalStmt.setInt(2, userid);
			
			rowsAffected = updateCurBalStmt.executeUpdate();
			
			if(rowsAffected != 1){
				dbErrorRollBackTx(dbConn);
				throw new ChatDbFailure(ChatDbFailure.INVALID_CREDENTIAL);
			}
			dbConn.commit(); /* Everything went OK */
			dbConn.close();
		//	System.out.println("Status updated to " +newStatus);
		} catch (SQLException ex) {
			dbErrorRollBackTx(dbConn);
		}
		
	}
	
	/**
	 * Updating Users with status
	 * @param status - online/offline
	 * @throws SQLException  When unable to connect to DB or failed Query
	 */
	public static void changeUserStatus(String name, String newStatus)throws ChatDbFailure,SQLException {
		Connection dbConn;
		int rowsAffected;
		String queryStr = "UPDATE user SET status = ? where name = ?;";
		
		dbConn = ChatAppDataSource.getConnection();
		
		// Turn off auto-commit so we can use transactions
        dbConn.setAutoCommit(false);

		// Update the balance 
		try (PreparedStatement updateCurBalStmt = dbConn.prepareStatement(queryStr)) {
			updateCurBalStmt.setString(1, newStatus);
			updateCurBalStmt.setString(2, name);
			
			rowsAffected = updateCurBalStmt.executeUpdate();
			
			if(rowsAffected != 1){
				dbErrorRollBackTx(dbConn);
				throw new ChatDbFailure(ChatDbFailure.INVALID_CREDENTIAL);
			}
			dbConn.commit(); /* Everything went OK */
			dbConn.close();
		//	System.out.println("Status updated to " +newStatus);
		} catch (SQLException ex) {
			dbErrorRollBackTx(dbConn);
		}
		
	}
	
	/**
	 * Updating PASSWORD
	 * @param status - online/offline
	 * @throws SQLException  When unable to connect to DB or failed Query
	 */
	public static void changeUserPassword(String userName, String password)throws ChatDbFailure,SQLException {
		Connection dbConn;
		int rowsAffected;
		String queryStr = "UPDATE user SET pass = ?  where name = ?;";
		
		dbConn = ChatAppDataSource.getConnection();
		
		// Turn off auto-commit so we can use transactions
        dbConn.setAutoCommit(false);

		// Update the balance 
		try (PreparedStatement updateCurBalStmt = dbConn.prepareStatement(queryStr)) {
			updateCurBalStmt.setString(1, password);
			updateCurBalStmt.setString(2, userName);
			
			rowsAffected = updateCurBalStmt.executeUpdate();
			
			if(rowsAffected != 1){
				dbErrorRollBackTx(dbConn);
				throw new ChatDbFailure(ChatDbFailure.BAD_USER);
				
			}	
		
			dbConn.commit(); /* Everything went OK */
			System.out.println("Password Updated Scuessfull !! ");
			
		} catch (SQLException ex) {
			dbErrorRollBackTx(dbConn);
		}
		
	}
	
	/**
	 * Selecting Users with status 
	 * @param status - online/offline
	 * @throws SQLException  When unable to connect to DB or failed Query
	 */
	public static List<User> printChatUsers(String status) throws SQLException {
		Connection dbConn;
		String queryStr = "SELECT userid, name " + "FROM user "
				+ "WHERE Status = '" + status + "'";
		
		dbConn = ChatAppDataSource.getConnection();
		Statement queryStmt = dbConn.createStatement();
		ResultSet results;
		
		List<User> onlineUsers = new ArrayList<User>();
		int userid;
		String name;
		
		results = queryStmt.executeQuery(queryStr);
		while (results.next()) { // process results
			
			User aUser = new User();
			
			userid = results.getInt("userid");
			name = results.getString("name");
			aUser.setId(userid);
			aUser.setName(name);
			onlineUsers.add(aUser);
			
		}
		
		// Free resources
		results.close();
		queryStmt.close();
		dbConn.close();
		return onlineUsers;
	}
	
	/**
	 * Selects Users with name field
	 * @param username - name
	 * @throws SQLException  When unable to connect to DB or failed Query
	 * @return ListOf User with Name as @param
	 */
	public static List<User> findAllUserWithName(String username) throws SQLException
	{
		Connection dbConn;
		String queryStr = "SELECT userid, name " + "FROM user "
				+ "WHERE name = '" + username + "'";
		
		dbConn = ChatAppDataSource.getConnection();
		Statement queryStmt = dbConn.createStatement();
		ResultSet results;
		
		List<User> userWithName = new ArrayList<User>();
		int userid;
		String name;
		
		results = queryStmt.executeQuery(queryStr);
		while (results.next()) { // process results
			
			User aUser = new User();
			
			userid = results.getInt("userid");
			name = results.getString("name");
			aUser.setId(userid);
			aUser.setName(name);
			userWithName.add(aUser);
			
		}
		
		// Free resources
		results.close();
		queryStmt.close();
		dbConn.close();
		return userWithName;
	}
	
	/**
	 * Selects Users with ID field
	 * @param userid - ID
	 * @throws SQLException  When unable to connect to DB or failed Query
	 * @return ListOf User with Name as @param
	 */
	public static User findUserWithId(int searchUserId) throws SQLException{
		
		Connection dbConn;
		String queryStr = "SELECT userid, name " + "FROM user "
				+ "WHERE userid = '" + searchUserId + "'";
		
		dbConn = ChatAppDataSource.getConnection();
		Statement queryStmt = dbConn.createStatement();
		ResultSet results;
		
		User userWithId= new User();
		int userid;
		String name;
		
		results = queryStmt.executeQuery(queryStr);
		while (results.next()) { // process results
			
			userid = results.getInt("userid");
			name = results.getString("name");
			userWithId.setId(userid);
			userWithId.setName(name);			
		}
		
		// Free resources
		results.close();
		queryStmt.close();
		dbConn.close();
		return userWithId;
	}

	/**
	 * @param userId
	 * @throws SQLException
	 * @throws ChatDbFailure 
	 */
	public static void sendMessege(Message msg) throws SQLException, ChatDbFailure
	{
		Connection dbConn;
		int rowsAffected;
		dbConn = ChatAppDataSource.getConnection();
		
		dbConn.setAutoCommit(false);
		
		//Two things are happening While Sending Message 
		
		//1. Inserting into mess 
		// SO IF THIS TROWS SQL EXCEPTION SECOND PART OF TRASACTION WILL NOT TABLE PLACE 
		String messageStm = "INSERT INTO `manisha_chat_project`.`message` (`content`,`senderid`) "
				+ "VALUES (?, ? ) ;";
				
		PreparedStatement queryStmt ;
		queryStmt = dbConn.prepareStatement(messageStm, Statement.RETURN_GENERATED_KEYS);
		queryStmt.setString(1, msg.getContent()); 
		queryStmt.setInt(2, msg.getSenderId()); 
			
		try {
			rowsAffected = queryStmt.executeUpdate();
			
			if(rowsAffected != 1){
				throw new ChatDbFailure(ChatDbFailure.INVALID_CREDENTIAL);
			}
			//dbConn.commit(); // make changes permanent
			System.out.println("Messge has created! now trying to generate the Receipt");
			int autoGeneratedMsgId ;
			ResultSet rs = queryStmt.getGeneratedKeys();
			rs.next();
		    autoGeneratedMsgId = rs.getInt(1);
			
		    //2. Creating Messege Receipt
			createMessageReceipt(dbConn,autoGeneratedMsgId, msg.getSenderId());
			dbConn.commit(); // make changes permanent /IF BOTH TRANSACTION ARE SUCESSFULL THEN Commint
			dbConn.close();
			System.out.println("Messege Sent Sucessfully !");
		} catch (SQLException ex) {
			dbErrorRollBackTx(dbConn); // call this function to give error and ROLLBACK
		}
	}
	
	/**
	 * Insert into messege_receipt Table 
	 * @param status - online/offline
	 * @throws SQLException  When unable to connect to DB or failed Query
	 */
	private static void  createMessageReceipt(Connection dbConn, int messegeId, int userId) throws SQLException{
		
	//	dbConn = ChatAppDataSource.getConnection();
		
		dbConn.setAutoCommit(false);

		String insertStmt = "INSERT INTO `manisha_chat_project`.`message_receipt`  (`messageid`, `userid`, `isRead`)  " +
				"VALUES (? , ? , ?);";
				
		PreparedStatement queryStmt ;
		queryStmt = dbConn.prepareStatement(insertStmt,Statement.RETURN_GENERATED_KEYS);
		queryStmt.setInt(1, messegeId); 
		queryStmt.setInt(2,userId);
		queryStmt.setInt(3,Receipt.UNREAD);

		queryStmt.executeUpdate();
		
		
		ResultSet rs = queryStmt.getGeneratedKeys();
		rs.next();
	    int autoGeneratedMsgId = rs.getInt(1);
	 //   dbConn.close();
		System.out.println("Receipt Generated with ID : " + autoGeneratedMsgId);
		
	}
	
	/**
	 * Delete User
	 * @param status - online/offline
	 * @throws SQLException  When unable to connect to DB or failed Query
	 * @throws ChatDbFailure 
	 */
	public static void deleteUser(String name) throws SQLException, ChatDbFailure{
		Connection dbConn;
		int rowsAffected;
		dbConn = ChatAppDataSource.getConnection();
		
		// Turn off auto-commit of db changes as they occur
		dbConn.setAutoCommit(false);
		
		String deleteStmt = "DELETE FROM `manisha_chat_project`.`user` WHERE `name`= ? ;";
				
		PreparedStatement queryStmt ;
		queryStmt = dbConn.prepareStatement(deleteStmt);
		queryStmt.setString(1, name); 
			
		try {
			rowsAffected = queryStmt.executeUpdate();
			
			if(rowsAffected != 1){
				throw new ChatDbFailure(ChatDbFailure.INVALID_CREDENTIAL);
			}
			dbConn.commit(); // make changes permanent
			dbConn.close();
			System.out.println("User Deleted Sucessfully !");
		} catch (SQLException ex) {
			dbErrorRollBackTx(dbConn); // call this function to give error and ROLLBACK
		}
	}
	
	public static void dbErrorRollBackTx(Connection dbConn) {
		try {
			System.out.println("DB access error – rollback changes");
			dbConn.rollback();
		} catch (SQLException ex) {
			System.out.println("Unable to rollback changes");
			ex.printStackTrace();
		}
	}
}

