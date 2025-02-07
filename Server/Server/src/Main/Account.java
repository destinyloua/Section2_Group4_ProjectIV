package Main;

public class Account {
	private int id;
	private String fName;
	private String lName;
	private String email;
	private String password;
	
	public Account(String fName, String lName, String email, String password) {
		this.fName = fName;
		this.lName = lName;
		this.email = email;
		this.password = password;
	}
	
	public String GetFName() {
		return fName;
	}
	
	public String GetLName() {
		return lName;
	}
	
	public String GetEmail() {
		return email;
	}
	
	public String GetPassword() {
		return password;
	}
	
	public Integer id() {
		return id;
	}
}
