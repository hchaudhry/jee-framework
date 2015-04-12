package controller;

import java.util.ArrayList;
import java.util.List;

import org.esgi.web.framework.context.interfaces.IContext;
import org.esgi.web.framework.router.interfaces.IRewriteRule;
import org.esgi.web.framework.router.interfaces.IRewriter;

public class Rewriter implements IRewriter {
	
	private List<IRewriteRule> rewriteRules = new ArrayList<>();

	public Rewriter(IRewriteRule... rules) {
		for(IRewriteRule rule:rules)
			addRule(rule);
	}
	
	@Override
	public void addRule(IRewriteRule rule) {
		rewriteRules.add(rule);
	}
	
	@Override
	public void rewrite(IContext request) {
		for(IRewriteRule rewriteRule : rewriteRules) {
			if(rewriteRule.matches(request)) {
				rewriteRule.rewrite(request);
				break;
			}
		}
	}
}
