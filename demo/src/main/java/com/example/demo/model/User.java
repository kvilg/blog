package com.example.demo.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Blob;
import java.util.*;

@Entity
public class User implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String login;

    private String password;


    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "userId")
    private Set<Post> postSet = new HashSet<>();

    private Blob img;


    public User() {


    }


    public User(String name, String login, String password) {
        this.name = name;
        this.login = login;
        this.password = password;
    }

    public User(String ssss) {
        this.name = ssss;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long idUser) {
        this.id = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Blob getImg() {
        return img;
    }

    public void setImg(Blob img) {
        this.img = img;
    }

    public Set<Post> getPostSet() {
        return postSet;
    }

    public void addPost(Post post) {
        this.postSet.add(post);
    }

}