package business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* Immutable */
final public class CheckoutRecord implements Serializable {
	
	private static final long serialVersionUID = 891234800414574886L;
	public static List<String> record_datas = new ArrayList<String>() {
    {
        add("Item1");
        add("Item2");
        add("Item3");
    }
    };
    private String orderNumber;
	private List<CheckoutRecordItem> itemIds;

	public static void addRecord(String title) {
    	record_datas.add(title);
    }
    public CheckoutRecord(String orderNum) {
		this.orderNumber = orderNum;
		itemIds = new ArrayList<CheckoutRecordItem>();
	}
	public void addOrderItems(CheckoutRecordItem item) {
		this.itemIds.add(item);
	}
	public List<CheckoutRecordItem> getItemIds() {
		return itemIds;
	}
	public String getorderNumber() {
		return orderNumber;
	}
	public String getId() {
		return orderNumber;
	}
	@Override
	public String toString() {
		return orderNumber;
	}
}