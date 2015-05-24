package modules.pages;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;

import org.esgi.web.framework.action.interfaces.IAction;
import org.esgi.web.framework.context.interfaces.IContext;

import com.fasterxml.jackson.databind.JsonNode;

import controller.FrontController;

public class HomePage implements IAction {

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

		String content = getContentFromFile(elements, context);

		try {
			context._getResponse().getWriter().println(content);
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

			try (BufferedReader br = new BufferedReader(new FileReader(
					"/home/hussam/Bureau/jee/FrontControllerProject/WEB-INF/views/"
							+ file + ".jsp"))) {
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
			context.setAttribute(contentType, content);

		}

		return content;
	}

}
