package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class TransactionHeader {
    private String transactionID;
    private String userID;
    private final DoubleProperty totalTransactionPrice; 

    public TransactionHeader(String transactionID, String userID) {
        this.transactionID = transactionID;
        this.userID = userID;
        this.totalTransactionPrice = new SimpleDoubleProperty(0);
    }


    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public DoubleProperty totalTransactionPriceProperty() {
        return totalTransactionPrice;
    }

    public double getTotalTransactionPrice() {
        return totalTransactionPrice.get();
    }

    public void setTotalTransactionPrice(double totalTransactionPrice) {
        this.totalTransactionPrice.set(totalTransactionPrice);
    }
}
