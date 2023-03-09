package business;

import java.io.Serializable;

/* Immutable */
final public class CheckoutRecord 
// implements Serializable 
{
	
	// private static final long serialVersionUID = 891234800414574888L;
	private String orderNumber;
	
    public CheckoutRecord(String orderNum) {
		this.orderNumber = orderNum;
	}
	public String getorderNumber() {
		return orderNumber;
	}
	public String getId() {
		return toString();
	}
	@Override
	public String toString() {
		return orderNumber;
	}
}