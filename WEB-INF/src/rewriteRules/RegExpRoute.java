package rewriteRules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.esgi.web.framework.context.interfaces.IContext;
import org.esgi.web.framework.router.interfaces.IRewriteRule;

import controller.Context;


public class RegExpRoute implements IRewriteRule{
	private final Pattern regex;
	private final String action;
	private final String[] params;
	
	public RegExpRoute(final String regex,final String action, final String[] params) {
		this.regex = Pattern.compile(regex);
		this.action = action;
		this.params = params;
	}
	
	public RegExpRoute(final String regex,final String action) {
		this.regex = Pattern.compile(regex);
		this.action = action;
		this.params = new String[0];
	}
	
	@Override
	public boolean matches(final IContext context) {
		return regex.matcher(context._getRequest().getRequestURI()).find();
	}

	@Override
	public void rewrite(final IContext context) {
		context.setActionClass(action);
		Matcher m = regex.matcher((context._getRequest().getRequestURI()));
		if(m.find()) {
			for(int i = 0;i < params.length && i < m.groupCount();++i) {
				((Context)context).setParameter(params[i], m.group(i + 1));
			}
		}
	}
}
