package controller;

import java.util.List;

import modules.user.User;

import org.esgi.web.framework.context.interfaces.IContext;
import org.esgi.web.framework.renderer.interfaces.IJSONGenericRenderer;

import com.google.gson.Gson;


public class RendererJson implements IJSONGenericRenderer {

	@Override
	public String render(final IContext context) {

		List<User> users = (List<User>) context.getAttribute("users");
		
		Gson gson = new Gson();
		
		String json = gson.toJson(users);
		
		return json;
	}
}
