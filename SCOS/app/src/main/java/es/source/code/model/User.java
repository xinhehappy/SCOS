package es.source.code.model;

import java.io.Serializable;

/**
 * Created by adam on 2016/6/18.
 */
public class User implements Serializable{

    private static final long serialVersionUID = 1L;
    public String userName;
    public String password;
    public boolean oldUser;
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

    public boolean isOldUser() {
        return oldUser;
    }

    public void setOldUser(boolean oldUser) {
        this.oldUser = oldUser;
    }

}
