/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.joca.lacomputadorafeliz.authentication;

/**
 *
 * @author joca
 */
public class PasswordVTO {

    public static final int MIN_LENGTH_PASSWORD = 12;
    public static final int MAX_LENGTH_PASSWORD = 100;

    private final String password;

    public PasswordVTO(String password, boolean encript) {
        PasswordEncripter encriptador = new PasswordEncripter();
        if (encript) {
            this.password = encriptador.encriptPassword(password);
        } else {
            this.password = password;
        }
    }

    public String getPassword() {
        return password;
    }

    public boolean isValid() {
        return password != null && !password.isBlank() && password.length() >= MIN_LENGTH_PASSWORD
                && password.length() <= MAX_LENGTH_PASSWORD;
    }
}
