/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.joca.lacomputadorafeliz.model.sales;

import com.joca.lacomputadorafeliz.model.computers.AssemblyDTO;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author joca
 */
public class Sale {
    
    private int iDSale;
    private List<AssemblyDTO> assembles;
    private String nit;
    private LocalDate date;
    private double totalValue;
    private double totalPrice;
    private String sellerUser;

    public int getiDSale() {
        return iDSale;
    }

    public void setiDSale(int iDSale) {
        this.iDSale = iDSale;
    }

    public List<AssemblyDTO> getAssembles() {
        return assembles;
    }

    public void setAssembles(List<AssemblyDTO> assembles) {
        this.assembles = assembles;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(double totalValue) {
        this.totalValue = totalValue;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getSellerUser() {
        return sellerUser;
    }

    public void setSellerUser(String sellerUser) {
        this.sellerUser = sellerUser;
    }
    
}
