package business;

import java.util.HashMap;
import java.util.List;

import dataaccess.User;

public interface ControllerInterface {
	public void login(String id, String password) throws LoginException;
	public List<String> allMemberIds();
	public List<String> allBookIds();
	public List<Book> allBookObj();
	public List<String> allBookCopyIds();
	public List<BookCopy> allBookCopyObj();
	public List<User> allUserObj();
	public List<String> allAddress();
	public List<Address> allAddressObj();
	public List<CheckoutRecord> allCheckoutRecordObj();
	public HashMap<String, String> allAddressHashmap();
	public List<String> allAuthors();
	public List<Author> allAuthorsObj();
	// public List<CheckoutRecord> allCheckoutRecordObj();
	public List<LibraryMember> allMembersObs();
	Book getBook(String bookIsbn);
	LibraryMember getMember(String text);
}
