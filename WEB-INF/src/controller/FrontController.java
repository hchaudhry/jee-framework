package controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modules.misc.VelocityInitializer;

import org.esgi.web.framework.context.interfaces.IContext;
import org.esgi.web.framework.context.interfaces.IHtmlContext;
import org.esgi.web.framework.core.interfaces.IFrontController;
import org.esgi.web.framework.router.interfaces.IDispatcher;
import org.esgi.web.framework.router.interfaces.IRewriter;

import rewriteRules.RegExpRoute;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

/**
 * HttpServlet implementation class FrontController
 */
public class FrontController extends HttpServlet implements IFrontController {

	private static final long serialVersionUID = 1L;
	private IDispatcher dispatcher;
	private IRewriter rewriter;

//	private List<User> users;
	private IContext context;

	private static String URIroot = "/FrontControllerProject/";
	
	private Map<String, Object> velocityContext;
	private IHtmlContext contextHtml;

	@Override
	public void init() throws ServletException {
		dispatcher = new Dispatcher();
		rewriter = new Rewriter(
		/*
		 * new RegExpRoute( "user/([0-9]+).html", "servlets.ExempleAction", new
		 * String[] {"id"} )
		 */
		// new FileRoute()
				new RegExpRoute(URIroot + "user/list/([a-z]+)",
						"modules.user.Liste", new String[] { "type" }),
				new RegExpRoute(URIroot + "user/list", "modules.user.Liste"),

				new RegExpRoute(URIroot + "user/add", "modules.user.Create"),

				new RegExpRoute(URIroot + "user/update/([1-9]+)",
						"modules.user.Update", new String[] { "id" }),

				new RegExpRoute(URIroot + "home", "modules.pages.HomePage"),
				new RegExpRoute(URIroot + "about", "modules.pages.About"),
				new RegExpRoute(URIroot + "contact", "modules.pages.Contact"));

//		users = new ArrayList<User>();
//		ArrayList<String> rules = new ArrayList<String>();

//		rules.add("admin");
//		rules.add("test");

//		users.add(new User(1, "log1", "toto", rules));
//		users.add(new User(2, "log2", "titi", rules));

		// Initiliaze Velocity
		VelocityInitializer.getInstance().initializeVelocity();
		velocityContext = new HashMap<String, Object>();
		
		super.init();
	}

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		handle(request, response);
	}

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response) {
		context = createContext(request, response);

//		context.setAttribute("users", users);
		context.setAttribute("velocityContext", velocityContext);
		
		contextHtml = context.toHtmlContext();
		contextHtml.addCssLink("http://localhost:8080/FrontControllerProject/css/bootstrap.css");
		contextHtml.addCssLink("http://localhost:8080/FrontControllerProject/css/freelancer.css");
		contextHtml.addCssLink("http://localhost:8080/FrontControllerProject/font-awesome/css/font-awesome.min.css");

		contextHtml.addJsLink("http://localhost:8080/FrontControllerProject/js/jquery.js");
		contextHtml.addJsLink("http://localhost:8080/FrontControllerProject/js/bootstrap.min.js");
		contextHtml.addJsLink("http://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.3/jquery.easing.min.js");
		contextHtml.addJsLink("http://localhost:8080/FrontControllerProject/js/classie.js");
		contextHtml.addJsLink("http://localhost:8080/FrontControllerProject/js/cbpAnimatedHeader.js");
		contextHtml.addJsLink("http://localhost:8080/FrontControllerProject/js/jqBootstrapValidation.js");
		contextHtml.addJsLink("http://localhost:8080/FrontControllerProject/js/contact_me.js");
		contextHtml.addJsLink("http://localhost:8080/FrontControllerProject/js/freelancer.js");
	
		rewriter.rewrite(context);
		dispatcher.dispatch(context);
	}

	private IContext createContext(HttpServletRequest request,
			HttpServletResponse response) {
		return new Context(request, response);
	}

	public List<Entry<String, JsonNode>> getTemplate(IContext context) {
		String requestUrl = context._getRequest().getRequestURI().toString();
		requestUrl = requestUrl.replace("/FrontControllerProject/", "");
		requestUrl = requestUrl.replace("/", "");

		ObjectMapper m = new ObjectMapper();
		JsonNode rootNode = null;
		try {
			rootNode = m.readTree(new File(
					"/home/hussam/Bureau/jee/FrontControllerProject/WEB-INF/templates/"
							+ requestUrl + ".json"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		JsonNode node = rootNode.path("module.html.layout");
		JsonNode headerNode = node.findValue("module.html.header");
		List<Entry<String, JsonNode>> elements = getElementsFromNode(headerNode);

		 JsonNode bodyNode = node.findValue("module.html.body");
		 elements.addAll(getElementsFromNode(bodyNode));

		 JsonNode footerNode = node.findValue("module.html.footer");
		 elements.addAll(getElementsFromNode(footerNode));
		
		return elements;
	}

	public List<Entry<String, JsonNode>> getElementsFromNode(JsonNode node) {

		Iterator<Entry<String, JsonNode>> it = node.fields();
		
		List<Entry<String, JsonNode>> myList = Lists.newArrayList(it);

		return myList;
	}
}
