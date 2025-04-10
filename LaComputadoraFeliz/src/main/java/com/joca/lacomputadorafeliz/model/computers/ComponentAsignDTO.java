/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.joca.lacomputadorafeliz.model.computers;

/**
 *
 * @author joca
 */
public class ComponentAsignDTO {
    
    private String componentName;
    private String computerName;
    private double componentValue;
    private double computerValue;
    private int amount;
    private int stock;

    public ComponentAsignDTO() {
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getComputerName() {
        return computerName;
    }

    public void setComputerName(String computerName) {
        this.computerName = computerName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getComponentValue() {
        return componentValue;
    }

    public void setComponentValue(double componentValue) {
        this.componentValue = componentValue;
    }

    public double getComputerValue() {
        return computerValue;
    }

    public void setComputerValue(double computerValue) {
        this.computerValue = computerValue;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    
    public boolean isValid() {
        return componentName != null && computerName != null && !componentName.isBlank() && !computerName.isBlank()
                && !componentName.isEmpty() && !computerName.isEmpty()
                && componentName.length() <= Component.MAX_LENGHT_NAME
                && computerName.length() <= Computer.MAX_LENGHT_NAME
                && amount > 0;
    }
}
