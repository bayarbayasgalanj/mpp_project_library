package rulesets;

import java.awt.Component;

import librarysystem.AddBookWindow;


/**
 * Rules:
 *  1. All fields must be nonempty 
 *  2. Price must be a floating point number with two decimal places 
 *  3. Price must be a number greater than 0.49. 
 */

public class AddBookRuleSet implements RuleSet {
	private AddBookWindow addBook;
	@Override
	public void applyRules(Component ob) throws RuleException {
		// TODO Auto-generated method stub
		addBook = (AddBookWindow) ob;
	}
	
}
