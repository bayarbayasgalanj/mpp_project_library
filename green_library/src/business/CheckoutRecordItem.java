package business;

import java.io.Serializable;
/* Immutable */
final public class CheckoutRecordItem implements Serializable {
	private static final long serialVersionUID = 8348574886L;
	
    private CheckoutRecord recordID;
    private BookCopy bookCopy;
	private Book bookID;
	private int due_date;
    
	public CheckoutRecordItem(CheckoutRecord record, BookCopy bc, int d) {
		recordID = record;
        bookCopy = bc;
		bookID = bc.getBook();
        due_date = d;
	}
	
	public CheckoutRecord getCheckOrder() {
		return recordID;
	}
	public BookCopy getBookCopy() {
		return bookCopy;
	}
	public Book getBook() {
		return bookID;
	}
	public int getDueDate(){
		return due_date;
	}
}