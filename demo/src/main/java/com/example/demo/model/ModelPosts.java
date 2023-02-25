package com.example.demo.model;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ModelPosts {
    public LinkedList<PostM> posts = new LinkedList<>();

    public ModelPosts(List<Post> postsUser) throws SQLException {

        for(Post p : postsUser){
            this.posts.add(new PostM(p));
        }

    }

    public ModelPosts() {

    }
    class PostM{
        public Long id;
        public String img;
        public String text;

        public LinkedList<CommentM>  comments = new LinkedList<>();

        public PostM(Post p) throws SQLException {
            this.text = p.getText();

            byte[] bytes = p.getImg().getBytes(1l, (int) p.getImg().length());
            this.img = new String(bytes);
            this.id = p.getId();


            for(Comment c : p.getComments()){
                this.comments.add(new CommentM(c));
            }

        }
    }

    class CommentM{
        public Long id;
        public String userName;
        public String text;

        public CommentM(Comment comment){
            this.id = comment.getId();
            this.userName = comment.getUser().getLogin();
            this.text = comment.getText();

        }
    }
}
