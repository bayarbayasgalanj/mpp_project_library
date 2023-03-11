package dataaccess;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import business.Book;
import business.BookCopy;
import business.CheckoutRecord;
import business.LibraryMember;
import business.Address;
import business.Author;
import dataaccess.DataAccessFacade.StorageType;


public class DataAccessFacade implements DataAccess {
	
	enum StorageType {
		BOOKS, MEMBERS, USERS, ADDRESS, ORDERRECORD, AUTHOR, BOOKCOPY, RECORD;
	}
	
	public static final String OUTPUT_DIR = System.getProperty("user.dir")
			// + "/src/dataaccess/storage"; //for Unix file system
			+ "/green_library/src/dataaccess/storage"; //for Unix file system
//			+ "\\src\\dataaccess\\storage"; //for Windows file system
	public static final String DATE_PATTERN = "MM/dd/yyyy";
	
	//implement: other save operations
	@Override
	public void saveNewAuthor(Author author) {
		HashMap<String, Author> authors = readAuthorMap();
		String authorId = author.getAuthorId();
		authors.put(authorId, author);
		saveToStorage(StorageType.AUTHOR, authors);	
	}
	@Override
	public void saveNewBook(Book book) {
		HashMap<String, Book> books = readBooksMap();
		String bookId = book.getIsbn();
		books.put(bookId, book);
		saveToStorage(StorageType.BOOKS, books);	
	}
	@Override
	public void saveNewBookCopy(BookCopy cop) {
		HashMap<String, BookCopy> cops = readBookCopyMap();
		String Id = cop.getId();
		cops.put(Id, cop);
		saveToStorage(StorageType.BOOKCOPY, cops);	
	}
	// @Override
    // public void saveNewCheckoutRecord(CheckoutRecord record) {

    // }
	@SuppressWarnings("unchecked")
	public  HashMap<String,BookCopy> readBookCopyMap() {
		//Returns a Map with name/value pairs being
		//   isbn -> Book
		Object retObj = readFromStorage(StorageType.BOOKCOPY);
		if (retObj==null){
			return new HashMap<String, BookCopy>();
		}else{
			return (HashMap<String, BookCopy>) retObj;
		}
	}
	public void removeAuthor(String addr_key) {
		HashMap<String, Author> addrs = readAuthorMap();
		addrs.remove(addr_key);
		saveToStorage(StorageType.AUTHOR, addrs);
	}
	public String getAuthorByKey(String author){
		HashMap<String, Author> authors = readAuthorMap();
		for (Map.Entry<String, Author> entry : authors.entrySet()) {
			String key = entry.getValue().getFirstName()+" "+entry.getValue().getLastName()+" "+entry.getValue().getBio();
			if (key.equals(author)){
				return entry.getKey();
			}
		}
		return null;
	}
	public void saveNewMember(LibraryMember member) {
		HashMap<String, LibraryMember> mems = readMemberMap();
		String memberId = member.getMemberId();
		mems.put(memberId, member);
		saveToStorage(StorageType.MEMBERS, mems);	
	}

	@Override
	public void saveNewCheckoutRecord(CheckoutRecord record) {
		HashMap<String, CheckoutRecord> records = readCheckoutMap();
        String recordId = record.getId();
        records.put(recordId, record);
        saveToStorage(StorageType.RECORD, records);
	}
	@SuppressWarnings("unchecked")
    public HashMap<String, CheckoutRecord> readUserRecords() {
        // Returns a Map with name/value pairs being
        HashMap<String, CheckoutRecord> map = (HashMap<String, CheckoutRecord>) readFromStorage(StorageType.RECORD);
        if (map == null)
            map = new HashMap<>();
        return map;
    }
	
	public void saveNewAddress(Address addr) {
		HashMap<String, Address> addrs = readAddressMap();
		String addrId = addr.getId();
		addrs.put(addrId, addr);
		saveToStorage(StorageType.ADDRESS, addrs);	
	}
	public void saveNewOrder(Address addr) {
		HashMap<String, Address> addrs = readAddressMap();
		String addrId = addr.getId();
		addrs.put(addrId, addr);
		saveToStorage(StorageType.ORDERRECORD, addrs);	
	}
	public void removeAddress(String addr_key) {
		HashMap<String, Address> addrs = readAddressMap();
		addrs.remove(addr_key);
		saveToStorage(StorageType.ADDRESS, addrs);
	}
	public Book getBookByIsbn(String isbn){
		HashMap<String, Book> books = readBooksMap();
		for (Map.Entry<String, Book> entry : books.entrySet()) {
			if (entry.getValue().getIsbn().equals(isbn)){
				return entry.getValue();
			}
		}
		return null;
	}
	public String getAddressByKey(String addr){
		HashMap<String, Address> addrs = readAddressMap();
		for (Map.Entry<String, Address> entry : addrs.entrySet()) {
			if (entry.getValue().getId().equals(addr)){
				return entry.getKey();
			}
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public  HashMap<String,Book> readBooksMap() {
		//Returns a Map with name/value pairs being
		//   isbn -> Book
		Object retObj = readFromStorage(StorageType.BOOKS);
		if (retObj==null){
			return new HashMap<String, Book>();
		}else{
			return (HashMap<String, Book>) retObj;
		}
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, LibraryMember> readMemberMap() {
		//Returns a Map with name/value pairs being
		//   memberId -> LibraryMember
		Object retObj = readFromStorage(StorageType.MEMBERS);
		if (retObj==null){
			return new HashMap<String, LibraryMember>();
		}else{
			return (HashMap<String, LibraryMember>) retObj;
		}
	}

	@Override
	public HashMap<String, CheckoutRecord> readCheckoutMap() {
		// TODO Auto-generated method stub
		Object retObj = readFromStorage(StorageType.RECORD);
		if (retObj==null){
			return new HashMap<String, CheckoutRecord>();
		}else{
			return (HashMap<String, CheckoutRecord>) retObj;
		}
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Author> readAuthorMap() {
		//Returns a Map with name/value pairs being
		Object retObj = readFromStorage(StorageType.AUTHOR);
		if (retObj==null){
			return new HashMap<String, Author>();
		}else{
			return (HashMap<String, Author>) retObj;
		}
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, Address> readAddressMap() {
		Object retObj = readFromStorage(StorageType.ADDRESS);
		if (retObj==null){
			return new HashMap<String, Address>();
		}else{
			return (HashMap<String, Address>) retObj;
		}
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, User> readUserMap() {
		//Returns a Map with name/value pairs being
		//   userId -> User
		Object retObj = readFromStorage(StorageType.USERS);
		if (retObj==null){
			return new HashMap<String, User>();
		}else{
			return (HashMap<String, User>) retObj;
		}
	}
	
	/////load methods - these place test data into the storage area
	///// - used just once at startup  
		
	static void loadBookMap(List<Book> bookList) {
		HashMap<String, Book> books = new HashMap<String, Book>();
		bookList.forEach(book -> books.put(book.getIsbn(), book));
		saveToStorage(StorageType.BOOKS, books);
	}
	static void loadBookCopyMap(List<BookCopy> copyList) {
		HashMap<String, BookCopy> cops = new HashMap<String, BookCopy>();
		copyList.forEach(cop -> cops.put(cop.getId(), cop));
		saveToStorage(StorageType.BOOKCOPY, cops);
	}
	static void loadUserMap(List<User> userList) {
		HashMap<String, User> users = new HashMap<String, User>();
		userList.forEach(user -> users.put(user.getId(), user));
		saveToStorage(StorageType.USERS, users);
	}
	
 
	static void loadMemberMap(List<LibraryMember> memberList) {
		HashMap<String, LibraryMember> members = new HashMap<String, LibraryMember>();
		memberList.forEach(member -> members.put(member.getMemberId(), member));
		saveToStorage(StorageType.MEMBERS, members);
	}
	static void loadAddressMap(List<Address> addrList) {
		HashMap<String, Address> addrs = new HashMap<String, Address>();
		addrList.forEach(addr -> addrs.put(addr.getId(), addr));
		saveToStorage(StorageType.ADDRESS, addrs);
	}
	static void loadAuthorMap(List<Author> addrList) {
		HashMap<String, Author> addrs = new HashMap<String, Author>();
		addrList.forEach(addr -> addrs.put(addr.getAuthorId(), addr));
		saveToStorage(StorageType.AUTHOR, addrs);
	}
	static void loadRecordMap(List<CheckoutRecord> addrList) {
		HashMap<String, CheckoutRecord> addrs = new HashMap<String, CheckoutRecord>();
		addrList.forEach(addr -> addrs.put(addr.getId(), addr));
		saveToStorage(StorageType.RECORD, addrs);
	}
	static void saveToStorage(StorageType type, Object ob) {
		ObjectOutputStream out = null;
		try {
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
			out = new ObjectOutputStream(Files.newOutputStream(path));
			out.writeObject(ob);
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			if(out != null) {
				try {
					out.close();
				} catch(Exception e) {}
			}
		}
	}
	
	static Object readFromStorage(StorageType type) {
		ObjectInputStream in = null;
		Object retVal = null;
		try {
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
			in = new ObjectInputStream(Files.newInputStream(path));
			retVal = in.readObject();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(in != null) {
				try {
					in.close();
				} catch(Exception e) {}
			}
		}
		return retVal;
	}
	
	
	
	final static class Pair<S,T> implements Serializable{
		
		S first;
		T second;
		Pair(S s, T t) {
			first = s;
			second = t;
		}
		@Override 
		public boolean equals(Object ob) {
			if(ob == null) return false;
			if(this == ob) return true;
			if(ob.getClass() != getClass()) return false;
			@SuppressWarnings("unchecked")
			Pair<S,T> p = (Pair<S,T>)ob;
			return p.first.equals(first) && p.second.equals(second);
		}
		
		@Override 
		public int hashCode() {
			return first.hashCode() + 5 * second.hashCode();
		}
		@Override
		public String toString() {
			return "(" + first.toString() + ", " + second.toString() + ")";
		}
		private static final long serialVersionUID = 5399827794066637059L;
	}











	
}
