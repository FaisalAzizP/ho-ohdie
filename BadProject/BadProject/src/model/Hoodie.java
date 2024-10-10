package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Hoodie {
	private String hoodieId;
	private String hoodieName;
	private double priceHoodie;

	public Hoodie(String hoodieId, String hoodieName, double priceHoodie) {
		super();
		this.hoodieId = hoodieId;
		this.hoodieName = hoodieName;
		this.priceHoodie = priceHoodie;

	}
	public String getHoodieId() {
		return hoodieId;
	}
	public void setHoodieId(String hoodieId) {
		this.hoodieId = hoodieId;
	}
	public String getHoodieName() {
		return hoodieName;
	}
	public void setHoodieName(String hoodieName) {
		this.hoodieName = hoodieName;
	}
	public double getPriceHoodie() {
		return priceHoodie;
	}
	public void setPriceHoodie(double priceHoodie) {
		this.priceHoodie = priceHoodie;
	}

	
	
	}
