package org.shared.dto;

import java.io.Serializable;

public class  UserDTO implements Serializable{
    private String userName;
    private String password;
    private int userId;
    
    public UserDTO(int userId,String userName,String password){
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    public int userId(){
        return userId;
    }

    public String userName(){
        return userName;
    }

    public String password(){
        return password;
    }

    
}
