package sample.MainSelection;


import javafx.beans.property.*;

public class MenuClass {

    private IntegerProperty itemId;
    private StringProperty itemName;
    private DoubleProperty itemPrice;

    public MenuClass(int i, String n, double p) {

        itemId  = new SimpleIntegerProperty(i);
        itemName = new SimpleStringProperty(n);
        itemPrice = new SimpleDoubleProperty(p);
    }

    public Integer getItemId(){return itemId.get();}


    public String getItemName() {
        return itemName.get();
    }

    public Double getItemPrice() {
        return itemPrice.get();
    }

}
