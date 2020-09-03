package com.bione.model;

public class Slots {

    public String name;
    public String text;
    public boolean selected;


    public Slots(String name, boolean selected, String text) {
        this.name = name;
        this.selected = selected;
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
