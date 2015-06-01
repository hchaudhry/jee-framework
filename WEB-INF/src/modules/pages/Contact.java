package modules.pages;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import modules.misc.VelocityUtil;

import org.esgi.web.framework.action.interfaces.IAction;
import org.esgi.web.framework.context.interfaces.IContext;
import org.esgi.web.framework.context.interfaces.IHtmlContext;

import com.fasterxml.jackson.databind.JsonNode;

import controller.FrontController;

public class Contact implements IAction{

	private Map<String, Object> velocityContext;
	private IHtmlContext contextHtml;

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
		List<Entry<String, JsonNode>> elements = ft.getTemplate(context);
		
		velocityContext = (Map<String, Object>) context.getAttribute("velocityContext");
		String content = getContentFromFile(elements, context);

		try {
			context._getResponse().getWriter().println(content);
			context.setAttribute("velocityContext", velocityContext);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getContentFromFile(List<Entry<String, JsonNode>> elem,
			IContext context) {

		String content = null;
		String contentType = null;
		String file = null;
		String value = null;

		for (int i = 0; i < elem.size(); i++) {

			value = elem.get(i).toString();
			String parts[] = value.split("=");
			contentType = parts[0];
			file = parts[1];
			file = file.replace("\"", "");
			
			if(content == null){
				content = VelocityUtil.getInstance().render(file + ".vm", velocityContext).toString();
				
				contextHtml = context.toHtmlContext();
				String[] cssLinks = contextHtml.getCssLinks();
				String[] jsLinks = contextHtml.getJsLinks();
				
				for (String link : cssLinks) {
					content += "<link href=\"" + link + "\" rel=\"stylesheet\" type=\"text/css\">";
				}
				
				for (String oneLink : jsLinks) {
					content += "<script src=\"" + oneLink +"\"></script>";
				}
			}
			else {
				content += VelocityUtil.getInstance().render(file + ".vm", velocityContext).toString();
			}
		}

		return content;
	}
}
