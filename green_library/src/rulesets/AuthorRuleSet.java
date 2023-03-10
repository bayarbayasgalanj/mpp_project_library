package rulesets;

import java.awt.Component;

import librarysystem.AddAuthorWindow;



/**
 * Rules:
 * 1. All fields must be nonempty
 * 2. Isbn must be numeric and consist of either 10 or 13 characters
 * 3. If Isbn has length 10, the first digit must be 0 or 1
 * 4. If Isbn has length 13, the first 3 digits must be either 978 or 979
 * 5. Price must be a floating point number with two decimal places 
 * 6. Price must be a number greater than 0.49.
 *
 */
public class AuthorRuleSet implements RuleSet {
	private AddAuthorWindow addAuthor;
	@Override
	public void applyRules(Component ob) throws RuleException {
		// TODO Auto-generated method stub
		addAuthor = (AddAuthorWindow) ob;
		isEmptyRule();
		isTelephoneNumber();
	}
	private void isTelephoneNumber() throws RuleException {
		String val = addAuthor.getTelephoneValue().trim();
		try{
			Integer.parseInt(val);
		} catch (NumberFormatException e){
			throw new RuleException("Phone number must be number");
		}
		if(val.length() != 10){
			throw new RuleException("Phone number must be 10 digits");
		}

	}
	private void isEmptyRule() throws RuleException{
		if(addAuthor.getFirstNameValue().trim().isEmpty() ||
		addAuthor.getLastnameValue().trim().isEmpty() ||
		addAuthor.getTelephoneValue().trim().isEmpty() ||
		addAuthor.getBioValue().trim().isEmpty()){
			throw new RuleException("All fields must be non empty!");
		}
	}

}