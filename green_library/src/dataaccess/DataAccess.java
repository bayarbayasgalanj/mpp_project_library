package dataaccess;

import java.util.HashMap;

import business.Address;
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
}
