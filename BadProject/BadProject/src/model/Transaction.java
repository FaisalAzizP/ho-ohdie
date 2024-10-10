package model;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Transaction {

    private String hoodieId;
    private String hoodieName;
    private Double priceHoodie;
    private int quantityHoodie;
    private final DoubleProperty totalPHoodie; 
    public Transaction(String hoodieId, String hoodieName, Double priceHoodie, int quantityHoodie) {
        this.hoodieId = hoodieId;
        this.hoodieName = hoodieName;
        this.priceHoodie = priceHoodie;
        this.quantityHoodie = quantityHoodie;
        this.totalPHoodie = new SimpleDoubleProperty(priceHoodie * quantityHoodie); 
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

    public Double getPriceHoodie() {
        return priceHoodie;
    }

    public void setPriceHoodie(Double priceHoodie) {
        this.priceHoodie = priceHoodie;
    }

    public int getQuantityHoodie() {
        return quantityHoodie;
    }

    public void setQuantityHoodie(int quantityHoodie) {
        this.quantityHoodie = quantityHoodie;
        this.totalPHoodie.set(priceHoodie * quantityHoodie); 
    }

    public DoubleProperty totalPHoodieProperty() {
        return totalPHoodie;
    }

    public double getTotalPHoodie() {
        return totalPHoodie.get();
    }
}
