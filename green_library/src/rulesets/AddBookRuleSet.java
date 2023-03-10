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
		isEmptyRules();
		isNumberRule();
		authorsCount();
	}
	private void authorsCount() throws RuleException {
		int count = addBook.getAuthorsCount();
		if(count == 0){
			throw new RuleException("Add at least one author please.");
		}
	}
	private void isEmptyRules() throws RuleException {
		if(addBook.getIsbnValue().trim().isEmpty() ||
		addBook.getTitleValue().trim().isEmpty() ||
		addBook.getMaxLengthValue().trim().isEmpty()){
			throw new RuleException("All fields must be non empty");
		}
	}
	private void isNumberRule() throws RuleException {
		String val = addBook.getIsbnValue().trim();
		try{
			if(val.length() == 13) {
				Long.parseLong(val);
			} else if(val.length() == 10){
				Integer.parseInt(val);
			}
		} catch (NumberFormatException e){
			throw new RuleException("ISBN must be numeric");
		}
		if(val.length() != 10 && val.length() != 13) {
			throw new RuleException("ISBN must be 10 or 13 digits");
		}
	}


	
}
