package modules.misc;

import java.io.IOException;

import org.esgi.web.framework.action.interfaces.IAction;
import org.esgi.web.framework.context.interfaces.IContext;

public class ErrorAction implements IAction {
	private final int number;
	private final String message;

	public ErrorAction(final int number, final String message) {
		this.number = number;
		this.message = message;
	}
	
	@Override
	public void proceed(final IContext context) {
		try {			
			context._getResponse().getWriter().write("erreur " + number + " :" + message);
		} catch (Exception e) {
			try {
				context._getResponse().getWriter().write("erreur " + number + " :" + message);
			} catch (IOException e1) {
			}
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
