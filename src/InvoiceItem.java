import java.io.Serializable;

public class InvoiceItem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2500028658262011102L;
	private Product product;
	private float quantity = 0;
	
/*-----------------------------------------------------------------------------------------*/
	
	public int getId() {
		return product.getId();
	}
	public String getName() {
		return product.getName();
	}
	public float getPricePerUnit() {
		return product.getPrice();
	}
	public float getQuantity() {
		return quantity;
	}
	public float getTotalAmount() {
		return product.getPrice()*quantity;
	}
	
/*-----------------------------------------------------------------------------------------*/
	
	public void setProduct(Product product) {
		this.product = product;
	}
	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}
	
}
