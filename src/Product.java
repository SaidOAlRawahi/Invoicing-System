
public class Product {
	private int productId;
	private String productName;
	private float productPrice;
	
/*-----------------------------------------------------------------------------------------*/
	
	public int getId() {
		return productId;
	}
	public float getPrice() {
		return productPrice;
	}
	public String getName() {
		return productName;
	}
	
/*-----------------------------------------------------------------------------------------*/
	
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public void setProductPrice(float productPrice) {
		this.productPrice = productPrice;
	}
	
}
