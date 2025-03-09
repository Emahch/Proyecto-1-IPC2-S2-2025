/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.joca.lacomputadorafeliz.model.users;

/**
 *
 * @author joca
 */
public class UserRol {
    
    private static final int MAX_LENGHT_NAME = 45;
    
    private int id;
    private String name;

    public UserRol() {
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public boolean isValid() {
        return this.name != null && !this.name.isBlank() && !this.name.isEmpty() && this.name.length() <= MAX_LENGHT_NAME;
    }
}
