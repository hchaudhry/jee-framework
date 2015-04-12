package modules.user;

import java.util.ArrayList;
import java.util.List;

public class User {
	
	public int id;
	public String login;
	public String password;
	public List<String> rules;
	
	public static final List<User> users = new ArrayList<>();
	
	public User(int id, final String login, final String password, final List<String> rules) {
		this.id = id;
		this.login = login;
		this.password = password;
		this.rules = rules;
	}
}