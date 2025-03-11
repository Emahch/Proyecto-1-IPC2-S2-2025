/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.joca.lacomputadorafeliz.model.sales;

/**
 *
 * @author joca
 */
public class SaleAssignment {
    
    private int iDSale;
    private int iDAssembly;
    private String computerName;

    public int getiDSale() {
        return iDSale;
    }

    public void setiDSale(int iDSale) {
        this.iDSale = iDSale;
    }

    public int getiDAssembly() {
        return iDAssembly;
    }

    public void setiDAssembly(int iDAssembly) {
        this.iDAssembly = iDAssembly;
    }

    public String getComputerName() {
        return computerName;
    }

    public void setComputerName(String computerName) {
        this.computerName = computerName;
    }
    
    public boolean isValid() {
        return this.iDAssembly > 0 && this.iDSale > 0;
    }
    
}
