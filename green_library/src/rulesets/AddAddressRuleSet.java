package rulesets;

import java.awt.Component;

import librarysystem.AddAddressWindow;
import librarysystem.AddBookWindow;


/**
 * Rules:
 *  1. All fields must be nonempty 
 *  2. Price must be a floating point number with two decimal places 
 *  3. Price must be a number greater than 0.49. 
 */

public class AddAddressRuleSet implements RuleSet {
	private AddAddressWindow addAddress;
	@Override
	public void applyRules(Component ob) throws RuleException {
		// TODO Auto-generated method stub
		addAddress = (AddAddressWindow) ob;
		isEmptyRules();
		isNumberRule();
	}
	private void isEmptyRules() throws RuleException {
		if(addAddress.getStreetValue().trim().isEmpty() ||
		addAddress.getCityValue().trim().isEmpty() ||
		addAddress.getStateValue().trim().isEmpty() ||
		addAddress.getZipValue().trim().isEmpty()){
			throw new RuleException("All fields must be non empty");
		}
	}
	private void isNumberRule() throws RuleException {
		String val = addAddress.getZipValue().trim();
		try{
			Integer.parseInt(val);
		} catch (NumberFormatException e){
			throw new RuleException("Zipcode must be numeric");
		}
		if(val.length() != 5) {
			throw new RuleException("Zipcode must be 5 digits");
		}
	}
	
}
