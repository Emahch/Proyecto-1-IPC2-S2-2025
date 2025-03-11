/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.joca.lacomputadorafeliz.model.sales;

/**
 *
 * @author joca
 */
public class Customer {
    
    public static final int MAX_LENGHT_NIT = 10;
    public static final int MAX_LENGHT_NAME = 100;
    public static final int MAX_LENGHT_ADDRESS = 100;
    
    private String nit;
    private String customerName;
    private String address;

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    public boolean isValid() {
        return this.nit != null && this.nit.matches("^[0-9-]+$") && this.nit.length() <= MAX_LENGHT_NIT
                && customerName != null && !customerName.isBlank() && !customerName.isEmpty() && customerName.length() <= MAX_LENGHT_NAME
                && address != null && !address.isBlank() && !address.isEmpty() && address.length() <= MAX_LENGHT_ADDRESS;
    }
    
}
