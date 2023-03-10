package rulesets;

import java.awt.Component;

import librarysystem.AddBookWindow;
import librarysystem.AddMemberWindow;


/**
 * Rules:
 *  1. All fields must be nonempty 
 *  2. Price must be a floating point number with two decimal places 
 *  3. Price must be a number greater than 0.49. 
 */

public class AddMemberRuleSet implements RuleSet {
	private AddMemberWindow addMember;
	@Override
	public void applyRules(Component ob) throws RuleException {
		// TODO Auto-generated method stub
		addMember = (AddMemberWindow) ob;
		isEmptyRules();
		isNumberRule();
	}
	
	private void isEmptyRules() throws RuleException {
		if(addMember.getFirstnameValue().trim().isEmpty() ||
		addMember.getLastnameString().trim().isEmpty() ||
		addMember.getTelephoneValue().trim().isEmpty() ||
		addMember.getMemberIdValue().trim().isEmpty()){
			throw new RuleException("All fields must be non empty");
		}
	}
	private void isNumberRule() throws RuleException {
		String val = addMember.getTelephoneValue().trim();
		try{
			Integer.parseInt(val);
		} catch (NumberFormatException e){
			throw new RuleException("Phone number must be numeric");
		}
		if(val.length() != 10 ) {
			throw new RuleException("Phone number must be 10 digits");
		}
	}


	
}