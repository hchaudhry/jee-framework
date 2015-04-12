package modules.user;

import java.io.IOException;

import org.esgi.web.framework.action.interfaces.IAction;
import org.esgi.web.framework.context.interfaces.IContext;

import controller.FrontController;
import controller.Renderer;
import controller.RendererJson;


public class Liste implements IAction{

	private Renderer renderer;
	private RendererJson renderJson;
	
	public Liste() {
		FrontController ft = new FrontController();
		ft.getTemplate();
		renderer = new Renderer();
		renderJson = new RendererJson();
	}
	
	@Override
	public int setPriority(int priority) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
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
		
		String type = (String) context.getParameter("type");
		String result = "";
		
		if (type == null)
			type = "html";
		
		
		switch (type) {
		case "html":
			result = renderer.render(context);
			break;
			
		case "json":
			result = renderJson.render(context);
			break;

		default:
			break;
		}
		
		try {
			context._getResponse().getWriter().println(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
