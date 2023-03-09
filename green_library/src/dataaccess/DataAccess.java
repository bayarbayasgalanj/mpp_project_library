package dataaccess;

import java.util.HashMap;

import business.Address;
import business.Author;
import business.Book;
import business.LibraryMember;
import dataaccess.DataAccessFacade.StorageType;

public interface DataAccess { 
	public HashMap<String,Book> readBooksMap();
	public HashMap<String,User> readUserMap();
	public HashMap<String, LibraryMember> readMemberMap();
	public HashMap<String, Address> readAddressMap();
	public void saveNewMember(LibraryMember member); 
	public void saveNewAddress(Address addr); 
	public void removeAddress(String addr_key); 
	public String getAddressByKey(String addr);
	public Address getAddressByKeyObj(String addr);
	public void saveNewAuthor(Author author); 
	public void removeAuthor(String addr_key); 
	public String getAuthorByKey(String addr); 
	public HashMap<String, Author> readAuthorMap();
}
