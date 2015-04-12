package rewriteRules;

import java.io.File;

import org.esgi.web.framework.context.interfaces.IContext;
import org.esgi.web.framework.router.interfaces.IRewriteRule;

import controller.Context;

public class FileRoute implements IRewriteRule{
	@Override
	public boolean matches(IContext context) {
		return getFileFrom(context).exists();
	}

	private File getFileFrom(IContext context) {
		return new File("." + context._getRequest().getRequestURI().substring(context._getRequest().getContextPath().length()));

	}
	
	@Override
	public void rewrite(IContext context) {
		File file = getFileFrom(context);
		context._getRequest();
		if (context.getUploadedFiles().length!=0)
			context.setActionClass("modules.statics.UploadAction");
		else if(file.isDirectory())
			context.setActionClass("modules.statics.DisplayDirectoryAction");
		else
			context.setActionClass("modules.statics.DownloadAction");
		((Context) context).setParameter("file",	file);
	}
}
