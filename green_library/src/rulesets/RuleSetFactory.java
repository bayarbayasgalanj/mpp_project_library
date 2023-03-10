package rulesets;

import java.awt.Component;
import java.util.HashMap;

import librarysystem.AddAuthorWindow;
import librarysystem.AddAddressWindow;
import librarysystem.AddBookWindow;
import librarysystem.AddMemberWindow;


final public class RuleSetFactory {
	private RuleSetFactory(){}
	static HashMap<Class<? extends Component>, RuleSet> map = new HashMap<>();
	static {
		map.put(AddAuthorWindow.class, new AuthorRuleSet());
		map.put(AddBookWindow.class, new AddBookRuleSet());
		map.put(AddMemberWindow.class, new AddMemberRuleSet());
		map.put(AddAddressWindow.class, new AddAddressRuleSet());
	}
	public static RuleSet getRuleSet(Component c) {
		Class<? extends Component> c1 = c.getClass();
		if(!map.containsKey(c1)) {
			throw new IllegalArgumentException("No RuleSet found for this component");
		}
		return map.get(c1);
	}
}
