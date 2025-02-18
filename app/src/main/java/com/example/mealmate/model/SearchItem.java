package com.example.mealmate.model;

import java.io.Serializable;

public class SearchItem implements Serializable {
    private String sectionName;
    private String itemName;

    public SearchItem(String sectionName, String itemName) {
        this.sectionName = sectionName;
        this.itemName = itemName;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
