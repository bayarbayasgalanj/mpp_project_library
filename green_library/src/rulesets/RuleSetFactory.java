package rulesets;

import java.awt.Component;
import java.util.HashMap;

import librarysystem.AddAuthorWindow;


final public class RuleSetFactory {
	private RuleSetFactory(){}
	static HashMap<Class<? extends Component>, RuleSet> map = new HashMap<>();
	static {
		map.put(AddAuthorWindow.class, new AuthorRuleSet());
	}
	public static RuleSet getRuleSet(Component c) {
		Class<? extends Component> c1 = c.getClass();
		if(!map.containsKey(c1)) {
			throw new IllegalArgumentException("No RuleSet found for this component");
		}
		return map.get(c1);
	}
}
