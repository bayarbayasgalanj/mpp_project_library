package business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;

public class SystemController implements ControllerInterface {
	public static Auth currentAuth = null;
	
	public void login(String id, String password) throws LoginException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, User> map = da.readUserMap();
		if(!map.containsKey(id)) {
			throw new LoginException("ID " + id + " not found");
		}
		String passwordFound = map.get(id).getPassword();
		if(!passwordFound.equals(password)) {
			throw new LoginException("Password incorrect");
		}
		currentAuth = map.get(id).getAuthorization();
	}
	@Override
	public List<String> allMemberIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		if (da.readMemberMap()!=null){
			retval.addAll(da.readMemberMap().keySet());
		}
		return retval;
	}
	@Override
	public List<LibraryMember> allMembersObs() {
		DataAccess da = new DataAccessFacade();
		List<LibraryMember> retval = new ArrayList<>();
		if (da.readMemberMap()!=null){
			da.readMemberMap().forEach((key, value) -> retval.add(value));
		}
		return retval;
	}
	@Override
	public List<String> allBookIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		if (da.readBooksMap()!=null){
			retval.addAll(da.readBooksMap().keySet());
		}
		return retval;
	}
	@Override
	public List<Book> allBookObj() {
		DataAccess da = new DataAccessFacade();
		List<Book> retval = new ArrayList<>();
		if(da.readBooksMap()!=null) {
			da.readBooksMap().forEach((key, value) -> retval.add(value));
		}
		return retval;
	}
	@Override
	public List<String> allBookCopyIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		if (da.readBooksMap()!=null){
			List<String> retval2 = new ArrayList<>();
			da.readBooksMap().forEach((key, value) -> {
				for (BookCopy bc: value.getCopies()){
					retval2.add(bc.toString());
				}
			});
			retval.addAll(retval2);
		}
		return retval;
	}
	@Override
	public List<BookCopy> allBookCopyObj() {
		DataAccess da = new DataAccessFacade();
		List<BookCopy> retval = new ArrayList<>();
		if(da.readBooksMap()!=null) {
			da.readBooksMap().forEach((key, value) -> {
				for (BookCopy bc: value.getCopies()){
					retval.add(bc);
				}
			});
		}
		return retval;
	}
	
	@Override
	public List<String> allAddress() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		if(da.readAddressMap()!=null) {
			retval.addAll(da.readAddressMap().keySet());
		}
		return retval;
	}
	@Override
	public List<CheckoutRecord> allCheckoutRecord() {
		DataAccess da = new DataAccessFacade();
		List<CheckoutRecord> retval = new ArrayList<>();
		
		// da.readCheckoutMap().forEach(
        //     (key, value)
        //         -> retval.add(value));
		return retval;
	}
	@Override
	public List<Address> allAddressObj() {
		DataAccess da = new DataAccessFacade();
		List<Address> retval = new ArrayList<>();
		if(da.readAddressMap()!=null) {
			da.readAddressMap().forEach((key, value) -> retval.add(value));
		}
		return retval;
	}
	@Override
	public HashMap<String, String> allAddressHashmap() {
		DataAccess da = new DataAccessFacade();
		HashMap<String, String> retval = new HashMap<>();
		if(da.readAddressMap()!=null) {
			da.readAddressMap().forEach((key, value) -> retval.put(key, value.toString()));
		}
		return retval;
	}
	@Override
	public List<String> allAuthors() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		if(da.readAuthorMap()!=null) {
			retval.addAll(da.readAuthorMap().keySet());
		}
		return retval;
	}
	@Override
	public List<Author> allAuthorsObj() {
		DataAccess da = new DataAccessFacade();
		List<Author> retval = new ArrayList<>();
		if(da.readAuthorMap()!=null) {
			da.readAuthorMap().forEach((key, value) -> retval.add(value));
		}
		return retval;
	}
}
