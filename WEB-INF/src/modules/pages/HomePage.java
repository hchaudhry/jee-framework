package modules.pages;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.esgi.web.framework.action.interfaces.IAction;
import org.esgi.web.framework.context.interfaces.IContext;

import controller.FrontController;

public class HomePage implements IAction{

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
		
	}

	@Override
	public boolean needsCredentials() {
		return false;
	}

	@Override
	public boolean hasCredential(String[] roles) {
		return false;
	}

	@Override
	public void proceed(IContext context) {
		
		FrontController ft = new FrontController();
		ft.getTemplate(context);
		
		String header = (String) context.getAttribute("header");
		header = header.replace("\"", "");
		
		String body = (String) context.getAttribute("body");
		body = body.replace("\"", "");
		
		String footer = (String) context.getAttribute("footer");
		footer = footer.replace("\"", "");
		
		String content = getContentFromFile(header);
		content += getContentFromFile(body);
		content += getContentFromFile(footer);
		
		try {
			context._getResponse().getWriter().println(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getContentFromFile(String fileName) {
		
		String content = null;
		try (BufferedReader br = new BufferedReader(new FileReader("/home/hussam/Bureau/jee/FrontControllerProject/WEB-INF/views/"+fileName+".jsp")))
		{
			String sCurrentLine;
 
			while ((sCurrentLine = br.readLine()) != null) {
				if (content == null)
					content = sCurrentLine;
				else
					content += sCurrentLine;
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return content;
	}

}
