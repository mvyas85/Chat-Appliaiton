package model;

import javax.servlet.http.HttpServletRequest;

import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = -5161680823918839255L;
	private int id;
	private String name;
	private String pass;
	private String status;
	
	public static final String ONLINE="online";
	public static final String OFFLINE="offline";
	
	public User() {
		super();
	}
	
	public User(String name, String pass) {
		super();
		this.name = name;
		this.pass = pass;
	}
	public User(HttpServletRequest request) {
		extractFormData(request);
	}	
	private void extractFormData(HttpServletRequest request) {
		
		if(!request.getParameter("name").equals(""))
			name = request.getParameter("name");
		if(!request.getParameter("password").equals(""))
			pass = request.getParameter("password");
	}

	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getPass() {
		return pass;
	}
	public String getStatus() {
		return status;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return " {id:" + id + ", name:\"" + name + "\", pass:\"" + pass
				+ "\", status:\"" + status + "\"}";
	}
	
}
