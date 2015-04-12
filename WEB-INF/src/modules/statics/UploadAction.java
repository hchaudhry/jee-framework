package modules.statics;

import java.io.File;
import java.io.OutputStream;
import java.nio.file.Files;

import org.esgi.web.framework.action.interfaces.IAction;
import org.esgi.web.framework.context.interfaces.IContext;

public class UploadAction implements IAction {
	@Override
	public void proceed(final IContext context) {

		try {
			File parent = (File) context.getParameter("file");
//			OutputStream out = context._getResponse().getOutputStream();
			for(File file : context.getUploadedFiles())
				file.renameTo(new File(parent.getAbsolutePath() + "/" + file.getName()));
			new DisplayDirectoryAction().proceed(context);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
