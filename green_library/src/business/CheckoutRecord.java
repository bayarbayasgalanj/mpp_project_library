package business;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/* Immutable */
final public class CheckoutRecord implements Serializable {
	public static final CheckoutRecord INSTANCE = new CheckoutRecord();

	private static final long serialVersionUID = 891234800414574886L;
	private LibraryMember recordID;
    private BookCopy bookCopy;
	private Book bookID;
	private LocalDate due_date;
	public static List<String> record_datas = new ArrayList<String>() {
    {
        add("Item1");
        add("Item2");
        add("Item3");
    }
    };
	private CheckoutRecord(){}
    private String orderNumber;
	private LibraryMember member;
	// private List<CheckoutRecordItem> itemIds;
	public static List<CheckoutRecordItem> itemIds;

	public static void addRecord(String title) {
    	record_datas.add(title);
    }
    // public CheckoutRecord(String orderNum, LibraryMember mem) {
	// 	orderNumber = orderNum;
	// 	member = mem;
	// 	itemIds = new ArrayList<CheckoutRecordItem>();
	// }
	public CheckoutRecord(String orderNum,LibraryMember member, BookCopy bc, LocalDate d) {
		recordID = member;
        bookCopy = bc;
		bookID = bc.getBook();
        due_date = d;
		orderNumber = orderNum;
	}
	// public LibraryMember getMember() {
	// 	return recordID;
	// }
	public BookCopy getBookCopy() {
		return bookCopy;
	}
	public Book getBook() {
		return bookID;
	}
	public LocalDate getDueDate(){
		return due_date;
	}

	public void addOrderItems(CheckoutRecordItem item) {
		itemIds.add(item);
	}
	public List<CheckoutRecordItem> getItemIds() {
		return itemIds;
	}
	public String getorderNumber() {
		return orderNumber;
	}
	public LibraryMember getMember() {
		return recordID;
	}
	public String getId() {
		return orderNumber;
	}
	@Override
	public String toString() {
		return orderNumber;
	}
}