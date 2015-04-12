package modules.statics;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import org.esgi.web.framework.action.interfaces.IAction;
import org.esgi.web.framework.context.interfaces.IContext;

public class DisplayDirectoryAction implements IAction{
	@Override
	public void proceed(final IContext context) {
		File file = (File)context.getParameter("file");
		PrintWriter writer;
		try {
			writer = context._getResponse().getWriter();
			if(file.getParent()!= null)
				writer.print(getLink("Aller au parent", file.getParent()));
			String[] children = file.list();
			Arrays.sort(children);
			StringBuilder filesStringBuilder = new StringBuilder();
			StringBuilder directoriesStringBuilder = new StringBuilder();
			for(String child : children) {
				File fileChild = new File(file.getPath() + "/" + child);
				if(fileChild.isFile())
					filesStringBuilder.append(getLink(child, fileChild.getPath()));
				else
					directoriesStringBuilder.append(getLink(child, fileChild.getPath()));
			}
			writer.print(filesStringBuilder.toString());
			writer.print(directoriesStringBuilder.toString());
			writer.print(getUploadForm(file.getPath()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private String getUploadForm(final String path) {
		return 	"<form method=\"POST\" enctype=\"multipart/form-data\" target=\"" + getUrl(path) + "\">"
			+	"<input type=\"file\" name=\"uploadedFile\"/>"
			+	"<input type=\"submit\" name=\"submit\">"
			+	"</form>";
	}
	private String getLink(final String name, final String path) {
		return "<a href=\"" + getUrl(path) + "\">" + name +" </a>" + "<br />";
	}
	
	private String getUrl(final String filePath) {
		return "./" + filePath;
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
}
