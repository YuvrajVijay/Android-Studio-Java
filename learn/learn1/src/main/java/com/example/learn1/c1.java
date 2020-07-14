package com.example.learn1;

public class c1 {
    private String name;
    private String color;
    private int model;

    public c1(String name, String color, int model) {
        this.name = name;
        this.color = color;
        this.model = model;
    }

    public c1() {
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public int getModel() {
        return model;
    }
    public void setColor(String color) {
        this.color = color;
    }

    public void setModel(int model) {
        this.model = model;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void des(){
        System.out.println(this.name+" "+this.model+" "+this.color);
    }
}
