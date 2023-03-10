package business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* Immutable */
final public class CheckoutRecordItem implements Serializable {
	
	private static final long serialVersionUID = 8348574886L;
	
    private CheckoutRecord recordID;
    private BookCopy bookCopy;
	private int quantity;
    private int unitPrice;
    private int totalPrice;

	public CheckoutRecordItem(CheckoutRecord record, BookCopy bc, int q, int u) {
		recordID = record;
        bookCopy = bc;
        quantity = q;
        unitPrice = u;
        totalPrice = q*u;
	}

	// public String getorderNumber() {
	// 	return orderNumber;
	// }
	// public String getId() {
	// 	return toString();
	// }
	// @Override
	// public String toString() {
	// 	return orderNumber;
	// }
}