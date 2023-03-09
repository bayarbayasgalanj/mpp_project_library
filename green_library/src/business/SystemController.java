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
		retval.addAll(da.readMemberMap().keySet());
		return retval;
	}
	
	@Override
	public List<String> allBookIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readBooksMap().keySet());
		return retval;
	}
	@Override
	public List<String> allAddress() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readAddressMap().keySet());
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
		da.readAddressMap().forEach(
            (key, value)
                -> retval.add(value));
		return retval;
	}
	@Override
	public HashMap<String, String> allAddressHashmap() {
		DataAccess da = new DataAccessFacade();
		HashMap<String, String> retval = new HashMap<>();
		da.readAddressMap().forEach(
            (key, value)
                -> retval.put(key, value.toString()));
		return retval;
	}
	
}
