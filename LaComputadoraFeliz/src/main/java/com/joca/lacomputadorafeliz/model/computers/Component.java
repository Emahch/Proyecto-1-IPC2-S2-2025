/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.joca.lacomputadorafeliz.model.computers;

/**
 *
 * @author joca
 */
public class Component{
    
    private static final int MAX_LENGHT_NAME = 30;
    
    private String name;
    private double value;
    private int amount;

    public Component() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    
    public boolean isValid() {
        return name != null && !name.isBlank() && !name.isEmpty() && name.length() <= MAX_LENGHT_NAME
                && value >= 0 && amount >= 0;
    }
}
