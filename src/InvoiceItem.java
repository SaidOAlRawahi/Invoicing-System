
public class InvoiceItem {

	private Product product = new Product();
	private float quantity;
	
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
	
	public void setProduct(Product product) {
		this.product = product;
	}
	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}
	
}
