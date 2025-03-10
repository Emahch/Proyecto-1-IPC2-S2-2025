/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.joca.lacomputadorafeliz.model.computers;

import java.util.List;

/**
 *
 * @author joca
 */
public class Computer {
    
    public static final int MAX_LENGHT_NAME = 30;
    
    private String name;
    private double price;
    private double value;
    private int amount;
    private List<Component> components;

    public Computer() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }
    
    public boolean isValid() {
        return name != null && !name.isBlank() && !name.isEmpty() && name.length() <= MAX_LENGHT_NAME
                && price > 0 && value >= 0 && amount >= 0;
    }
}
