package com.joca.lacomputadorafeliz.model.users;

/**
 *
 * @author emahch
 */
public class User {
    
    public static final int MIN_LENGTH_PASSWORD = 6;
    public static final int MAX_LENGTH_PASSWORD = 50;
    public static final int MAX_LENGTH_NAME = 100;
    public static final int MAX_LENGTH_USERNAME = 30;
    
    private String name;
    private String userName;
    private String password;
    private UserRol userRol;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRol getUserRol() {
        return userRol;
    }

    public void setUserRol(UserRol userType) {
        this.userRol = userType;
    }
    
    public boolean isValid(){
        return name != null && !name.isBlank() && name.length() <= MAX_LENGTH_NAME
                && userName != null && !userName.isBlank() && userName.length() <= MAX_LENGTH_USERNAME && !userName.contains(" ")
                && password != null && !password.isBlank() && password.length() >= MIN_LENGTH_PASSWORD 
                && password.length() <= MAX_LENGTH_PASSWORD
                && userRol != null;
    }
}
