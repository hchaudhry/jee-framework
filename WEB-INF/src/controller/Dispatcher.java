package controller;

import modules.misc.ErrorAction;

import org.esgi.web.framework.action.interfaces.IAction;
import org.esgi.web.framework.context.interfaces.IContext;
import org.esgi.web.framework.router.interfaces.IDispatcher;

public class Dispatcher implements IDispatcher{

	@Override
	public void dispatch(IContext context) {
		try {
			((IAction)Class.forName(context.getActionClass()).newInstance()).proceed(context);
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException | NullPointerException e) {
			new ErrorAction(404, "action non trouvée").proceed(context);
			e.printStackTrace();
		}
	}
}
