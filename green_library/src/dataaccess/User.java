package dataaccess;

import java.io.Serializable;

import business.ControllerInterface;
import business.SystemController;

final public class User implements Serializable {
	private static final long serialVersionUID = 5147265048973262104L;
	public static final User INSTANCE = new User();
	public static boolean isLogin = false;
	public static Auth loginAuth = null;
	static ControllerInterface ci = new SystemController();
	private String id;
	
	private String password;
	private Auth authorization;
	private User(){}
	User(String id, String pass, Auth  auth) {
		this.id = id;
		this.password = pass;
		this.authorization = auth;
	}
	
	public String getId() {
		return id;
	}
	public String getPassword() {
		return password;
	}
	public Auth getAuthorization() {
		return authorization;
	}
	@Override
	public String toString() {
		return "[" + id + ":" + password + ", " + authorization.toString() + "]";
	}
	public static User getLoginCheck(String user, String pass){
		for (User u: ci.allUserObj()){
			if (u.getId().equals(user) && u.getPassword().equals(pass)){
				isLogin = true;
				loginAuth = u.getAuthorization();
				return u;
			}
		}
		return null;
	}
	public static void setNowLogin(boolean log){
		isLogin = log;
		if (!isLogin){
			loginAuth = null;
		}
	}
	public static boolean getNowLogin(){
		return isLogin;
	}
	public Auth getNowAuth(){
		return loginAuth;
	}
}
