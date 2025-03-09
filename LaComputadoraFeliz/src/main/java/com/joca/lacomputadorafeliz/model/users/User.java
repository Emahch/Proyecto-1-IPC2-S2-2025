package com.joca.lacomputadorafeliz.model.users;

/**
 *
 * @author emahch
 */
public class User{
    public static final int MAX_LENGTH_NAME = 100;
    public static final int MAX_LENGTH_USERNAME = 30;
    
    private String name;
    private String userName;
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

    public UserRol getUserRol() {
        return userRol;
    }

    public void setUserRol(UserRol userType) {
        this.userRol = userType;
    }
    
    public boolean isValid(){
        return name != null && !name.isBlank() && name.length() <= MAX_LENGTH_NAME
                && userName != null && !userName.isBlank() && userName.length() <= MAX_LENGTH_USERNAME && !userName.contains(" ")
                && userRol != null;
    }
}
