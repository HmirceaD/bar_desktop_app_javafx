package sample.MenuItems;


import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class TabMenuClass {

    private StringProperty itemName;
    private DoubleProperty itemPrice;
    private IntegerProperty itemQuantity;

    public TabMenuClass(String name, double price){

    }

    public String getItemName() {
        return itemName.get();
    }

    public StringProperty itemNameProperty() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName.set(itemName);
    }

    public double getItemPrice() {
        return itemPrice.get();
    }

    public DoubleProperty itemPriceProperty() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice.set(itemPrice);
    }

    public int getItemQuantity() {
        return itemQuantity.get();
    }

    public IntegerProperty itemQuantityProperty() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity.set(itemQuantity);
    }
}
