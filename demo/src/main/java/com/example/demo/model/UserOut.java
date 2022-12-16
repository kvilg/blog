package com.example.demo.model;

import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class UserOut {

    private Long id;

    private String name;

    private String login;

    private String token ;

    private String img;


    public UserOut(User user,String token){
        this.id = user.getId();
        this.name = user.getName();
        this.login = user.getLogin();
        this.token = token;
    }

    public UserOut(User user) throws SQLException {
        this.id = user.getId();
        this.name = user.getName();
        this.login = user.getLogin();



        if(user.getImg() != null){
            byte[] bytes = user.getImg().getBytes(1l, (int) user.getImg().length());

            this.img = new String(bytes);
        }

    }
}
