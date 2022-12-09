package com.example.demo.model;

import javax.persistence.*;
import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Post {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String text;

    private Blob img;



    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "postId")
    private Set<Comment> comments = new HashSet<>();

    public Post(){

    }

    public Post(String img, String text) throws SQLException {

        byte[] byteData = img.getBytes();

        this.img = new SerialBlob(byteData);
        this.text = text;


    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Blob getImg() {
        return img;
    }

    public void setImg(Blob img) {
        this.img = img;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void addComments(Comment comments) {
        this.comments.add(comments);
    }
}
