import java.io.Serializable;

public class Product implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3413834256792953428L;
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
	
	public void setId(int productId) {
		this.productId = productId;
	}
	public void setName(String productName) {
		this.productName = productName;
	}
	public void setPrice(float productPrice) {
		this.productPrice = productPrice;
	}
	
}
