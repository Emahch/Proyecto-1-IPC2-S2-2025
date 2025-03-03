/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.joca.lacomputadorafeliz.authentication;

import java.math.BigInteger;

/**
 *
 * @author joca
 */
public class PasswordEncripter {
    
    /**
     * Obtiene la contraseña y la encripta
     *
     * @param password
     * @return contraseña encriptada
     */
    public String encriptPassword(String password) {
        return String.format("%x", new BigInteger(1, password.getBytes()));
    }
}
