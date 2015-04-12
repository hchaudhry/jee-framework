package controller;

import java.util.Iterator;
import java.util.List;

import modules.user.User;

import org.esgi.web.framework.context.interfaces.IContext;
import org.esgi.web.framework.renderer.interfaces.IRenderer;

public class Renderer implements IRenderer {

	private StringBuilder text;

	@Override
	public String render(final IContext context) {

		List<User> users = (List<User>) context.getAttribute("users");

		Iterator<User> iterator = users.iterator();

		text = new StringBuilder();
		text.append("<table border='1'><tr><td>Id</td><td>Login</td><td>Password</td><td>Roles</td></tr>");

		while (iterator.hasNext()) {
			User user = (User) iterator.next();

			text.append("<tr><td>" + user.id + "</td><td>" + user.login + "</td><td>" + user.password + "</td><td> A Faire </td></tr>");
		}

		text.append("</table>");

		return text.toString();
	}
}
