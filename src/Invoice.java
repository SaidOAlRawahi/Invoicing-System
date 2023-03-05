import java.util.ArrayList;

public class Invoice {
	private String customerFirstName;
	private String customerLastName;
	private int customerPhoneNo;
	private String initiatedDate;
	private ArrayList<InvoiceItem> items;
	private float paidAmount;
	
	public String getCustomerName() {
		return customerFirstName + " " + customerLastName;
	}
	public int getCustomerPhoneNo() {
		return customerPhoneNo;
	}
	public String getDate() {
		return initiatedDate;
	}
	public int getItemsCount() {
		return items.size();
	}
	public float getTotalAmount() {
		float totalAmount = 0;
		for (InvoiceItem i : items) {
			totalAmount += i.getTotalAmount();
		}
		return totalAmount;
	}
	
	public float getAmountPaid() {
		return paidAmount;
	}
	public float getBalance() {
		return getAmountPaid() - getTotalAmount();
	}
	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}
	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
	}
	public void setCustomerPhoneNo(int customerPhoneNo) {
		this.customerPhoneNo = customerPhoneNo;
	}
	public void setInitiatedDate(String initiatedDate) {
		this.initiatedDate = initiatedDate;
	}
	public void setItems(ArrayList<InvoiceItem> items) {
		this.items = items;
	}
	public void setPaidAmount(float paidAmount) {
		this.paidAmount = paidAmount;
	}
}
