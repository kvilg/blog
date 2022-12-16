package com.example.demo.model;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ModelComments {
    public LinkedList<ModelComments.CommentsM> comments = new LinkedList<>();

    public ModelComments(List<Comment> comments) throws SQLException {

        for(Comment c : comments){
            this.comments.add(new ModelComments.CommentsM(c));
        }

    }


    public class CommentsM {
        public String userName;
        public String text;

        public CommentsM(Comment c) {
            this.userName = c.getUser().getName();
            this.text = c.getText();
        }
    }
}
