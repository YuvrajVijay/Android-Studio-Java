package com.yuvrajvi.babyneeds.model;

public class Details {
    private String babyItem;
    private int itemQuantity;
    private int itemSize;
    private String itemColor;
    private String date_created;


    public Details() {

    }

    public Details(String babyItem, int itemQuantity, int itemSize, String itemColor,String date_created) {
        this.babyItem = babyItem;
        this.itemQuantity = itemQuantity;
        this.itemSize = itemSize;
        this.itemColor = itemColor;
        this.date_created=date_created;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public String getBabyItem() {
        return babyItem;
    }

    public void setBabyItem(String babyItem) {
        this.babyItem = babyItem;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public int getItemSize() {
        return itemSize;
    }

    public void setItemSize(int itemSize) {
        this.itemSize = itemSize;
    }

    public String getItemColor() {
        return itemColor;
    }

    public void setItemColor(String itemColor) {
        this.itemColor = itemColor;
    }
}
