package modules.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.esgi.web.framework.action.interfaces.IAction;
import org.esgi.web.framework.context.interfaces.IContext;

public class Create implements IAction {

	private List<User> users;

	@Override
	public int setPriority(int priority) {
		return 0;
	}

	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	public void addCredential(String role) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean needsCredentials() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasCredential(String[] roles) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void proceed(IContext context) {

		try {

			PrintWriter out = context._getResponse().getWriter();
			out.print("<form method='POST'> Login : <input type='text' name='login' /> Password : <input type='text' name='password' /> <input type='submit' value='submit' /></form>");

			if (context._getRequest().getParameter("login") != null
					&& context._getRequest().getParameter("password") != null) {

				ArrayList<String> rules = new ArrayList<String>();
				rules.add("admin");

				String login = context._getRequest().getParameter("login");
				String password = context._getRequest()
						.getParameter("password");

				users = (List<User>) context.getAttribute("users");
				User lastUser = users.get(users.size() - 1);
				
				User user = new User(lastUser.id+1, login, password, rules);

				users.add(user);

				context.setAttribute("users", users);

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
