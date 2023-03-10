import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Invoice implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2667564412454072542L;
	private int invoiceId;
	private String customerFirstName;
	private String customerLastName;
	private int customerPhoneNo;
	private LocalDate initiatedDate;
	private float paidAmount;
	ArrayList<InvoiceItem> items = new ArrayList<InvoiceItem>();
	
	
/*-----------------------------------------------------------------------------------------*/
	
	public String getCustomerFirstName() {
		return customerFirstName;
	}
	public String getCustomerLastName() {
		return customerLastName;
	}
	public int getCustomerPhoneNo() {
		return customerPhoneNo;
	}
	public LocalDate getDate() {
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
	public int getInvoiceNo() {
		return invoiceId;
	}
	public float getBalance() {
		return getAmountPaid() - getTotalAmount();
	}

/*-----------------------------------------------------------------------------------------*/
	
	
	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}
	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
	}
	public void setCustomerPhoneNo(int customerPhoneNo) {
		this.customerPhoneNo = customerPhoneNo;
	}
	public void setInitiatedDate(LocalDate initiatedDate) {
		this.initiatedDate = initiatedDate;
	}
	public boolean setPaidAmount(float paidAmount) {
		if (paidAmount >= getTotalAmount()) {
			this.paidAmount = paidAmount;
			return true;
		}
		else {
			return false;
		}
	}
	public void setInvoiceNo(int invoiceId) {
		this.invoiceId = invoiceId;
	}
}
