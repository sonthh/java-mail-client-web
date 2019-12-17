package constant;

import entity.UserLogin;

public class Environment {
	public static Integer PAGE_SIZE = 3;
	public static String username = "tranhuuhongson@gmail.com";
	public static String password = "tranhuuhongSon1998@";

	public static void setAccount(UserLogin userLogin) {
		username = userLogin.getUsername();
		password = userLogin.getPassword();
	}
	
	public static void resetAccount() {
		username = "";
		password = "";
	}
}
