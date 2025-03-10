/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.joca.lacomputadorafeliz.model.computers;

import static com.joca.lacomputadorafeliz.model.users.User.MAX_LENGTH_USERNAME;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author joca
 */
public class AssemblyDTO {

    private int id;
    private String computerName;
    private String assemblerUser;
    private LocalDate date;
    private double totalValue;
    private double computerPrice;
    private ComputerStateEnum state;

    public AssemblyDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComputerName() {
        return computerName;
    }

    public void setComputerName(String computerName) {
        this.computerName = computerName;
    }

    public String getAssemblerUser() {
        return assemblerUser;
    }

    public void setAssemblerUser(String assemblerUser) {
        this.assemblerUser = assemblerUser;
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
    
    public String getDateFormatted() {
        return this.date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public ComputerStateEnum getState() {
        return state;
    }

    public double getComputerPrice() {
        return computerPrice;
    }

    public void setComputerPrice(double computerPrice) {
        this.computerPrice = computerPrice;
    }

    public void setState(ComputerStateEnum state) {
        this.state = state;
    }

    public boolean isValid() {
        return computerName != null && !computerName.isBlank() && !computerName.isEmpty() && computerName.length() <= Computer.MAX_LENGHT_NAME
                && assemblerUser != null && !assemblerUser.isBlank() && assemblerUser.length() <= MAX_LENGTH_USERNAME && !assemblerUser.contains(" ");
    }

}
