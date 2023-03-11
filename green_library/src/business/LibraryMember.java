package business;

import java.io.Serializable;

final public class LibraryMember extends Person implements Serializable {
	private String memberId;
	
	public LibraryMember(String memberId, String fname, String lname, String tel,Address add) {
		super(fname,lname, tel, add);
		this.memberId = memberId;		
	}
	
	public String getMemberId() {
		return memberId;
	}

	@Override
	public String toString() {
		return "Member: "+ getMemberId() + " " + getFirstName() + " " + getLastName() + 
				", " + getTelephone()  + " ADDRESS: " + getAddress();
	}

	private static final long serialVersionUID = -2226197306790714013L;

    public void updateMember(String memberId, String fname, String lname, String tel,Address add) {
		this.setFirstName(fname);
		this.setLastName(lname);
		this.setTelephone(tel);
		this.setAddress(add);
		this.memberId = memberId;
    }
}
