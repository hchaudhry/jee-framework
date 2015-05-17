package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modules.user.User;

import org.esgi.web.framework.context.interfaces.IContext;
import org.esgi.web.framework.core.interfaces.IFrontController;
import org.esgi.web.framework.router.interfaces.IDispatcher;
import org.esgi.web.framework.router.interfaces.IRewriter;

import rewriteRules.RegExpRoute;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * HttpServlet implementation class FrontController
 */
public class FrontController extends HttpServlet implements IFrontController {
	
	private static final long serialVersionUID = 1L;
	private IDispatcher dispatcher;
	private IRewriter rewriter;
	
	private List<User> users;
	private IContext context;
	
	private static String URIroot = "/FrontControllerProject/";
	
	@Override
	public void init() throws ServletException {
		dispatcher = new Dispatcher();
		rewriter = new Rewriter(
			/*new  RegExpRoute(
				"user/([0-9]+).html",
				"servlets.ExempleAction",
				new String[] {"id"}
			)*/
//			new FileRoute()
			new RegExpRoute(URIroot + "user/list/([a-z]+)", "modules.user.Liste", new String[] {"type"}),
			new RegExpRoute(URIroot + "user/list", "modules.user.Liste"),
			
			new RegExpRoute(URIroot + "user/add", "modules.user.Create"),
			
			new RegExpRoute(URIroot + "user/update/([1-9]+)", "modules.user.Update", new String[] {"id"}),
			
			new RegExpRoute(URIroot + "home", "modules.pages.HomePage")
		);
		
		users = new ArrayList<User>();
		ArrayList<String> rules = new ArrayList<String>();
		
		rules.add("admin");
		rules.add("test");
		
		users.add(new User(1, "log1", "toto", rules));
		users.add(new User(2, "log2", "titi", rules));
		
		super.init();
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		handle(request, response);
	}
	
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response) {
		context = createContext(request,response);
		
		context.setAttribute("users", users);
		
		rewriter.rewrite(context);
		dispatcher.dispatch(context);
	}
	
	private IContext createContext(HttpServletRequest request,HttpServletResponse response) {
		return new Context(request,response);
	}
	
	public void getTemplate(IContext context)
	{
		ObjectMapper m = new ObjectMapper();
		JsonNode rootNode = null;
		try {
			rootNode = m.readTree(new File("/home/hussam/Bureau/jee/FrontControllerProject/WEB-INF/templates/default.json"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JsonNode node = rootNode.path("module.html.layout");
		JsonNode headerNode = node.findValue("module.html.header");
		JsonNode header = headerNode.findValue("head");
		
		JsonNode bodyNode = node.findValue("module.html.body");
		JsonNode body = bodyNode.findValue("body");
		
		JsonNode footerNode = node.findValue("module.html.footer");
		JsonNode footer = footerNode.findValue("footer");
		
		context.setAttribute("header", header.toString());
		context.setAttribute("body", body.toString());
		context.setAttribute("footer", footer.toString());
	}
}
