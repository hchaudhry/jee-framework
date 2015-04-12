package controller;

import org.esgi.web.framework.action.interfaces.IAction;

public abstract class BaseAction implements IAction {
	
	private int priority;
	
	@Override
	public int setPriority(final int priority) {
		return this.priority = priority;
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return priority;
	}

	@Override
	public void addCredential(final String role) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean hasCredential(final String[] roles) {
		// TODO Auto-generated method stub
		return false;
	}
}
