package model;

public class Cartdata {

	private String userId;
	private String hoodieId;
	private int quantity;
	public Cartdata(String userId, String hoodieId, int quantity) {
		super();
		this.userId = userId;
		this.hoodieId = hoodieId;
		this.quantity = quantity;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getHoodieId() {
		return hoodieId;
	}
	public void setHoodieId(String hoodieId) {
		this.hoodieId = hoodieId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	

}
