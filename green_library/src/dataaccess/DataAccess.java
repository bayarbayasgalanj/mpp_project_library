package dataaccess;

import java.util.HashMap;
import java.util.List;

import business.Address;
import business.Author;
import business.Book;
import business.BookCopy;
import business.CheckoutRecord;
import business.LibraryMember;

public interface DataAccess { 
	public HashMap<String,Book> readBooksMap();
	public HashMap<String,BookCopy> readBookCopyMap();
	public void saveNewBookCopy(BookCopy cop);
	public void updateBookCopy(BookCopy cop);
	public void updateBook(Book book);
	public LibraryMember getMemberById(String id);
	public void updateMember(LibraryMember member);
	public void saveNewBook(Book book);
	public HashMap<String,User> readUserMap();
	public HashMap<String, LibraryMember> readMemberMap();
	public HashMap<String, Address> readAddressMap();
	public HashMap<String, CheckoutRecord> readCheckoutMap();
	public void saveNewMember(LibraryMember member); 
	public void saveNewAddress(Address addr); 
	public void removeAddress(String addr_key); 
	public String getAddressByKey(String addr);
	public Book getBookByIsbn(String isbn);
	public void saveNewAuthor(Author author); 
	public void removeAuthor(String addr_key); 
	public String getAuthorByKey(String addr); 
	public HashMap<String, Author> readAuthorMap();
	public void saveNewCheckoutRecord(CheckoutRecord record);
	List<CheckoutRecord> readUserRecords(LibraryMember member);
	public List<CheckoutRecord> readBookCopyRecords();
	public void saveAndUpdateBook(BookCopy bookCopy);
}
