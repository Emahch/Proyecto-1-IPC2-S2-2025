/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.joca.lacomputadorafeliz.model.computers;

/**
 *
 * @author joca
 */
public enum ComputerStateEnum {
    EN_VENTA("En venta"),
    VENDIDA("Vendida"),
    DEVUELTA("Devuelta");
    
    private final String publicName;

    private ComputerStateEnum(String publicName) {
        this.publicName = publicName;
    }

    public String getPublicName() {
        return publicName;
    }
}
