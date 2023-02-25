package com.example.demo.servis;

import com.example.demo.model.Comment;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.repo.CommentRepo;
import com.example.demo.repo.PostRepo;
import com.example.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CommentService {

    @Autowired
    private PostRepo dataPost;
    @Autowired
    private UserRepo dataUser;
    @Autowired
    private CommentRepo dataComment;

    public void addComment(String userLogin,Long postId,String text){

        User user = dataUser.getByLogin(userLogin);

        Post post = dataPost.getById(postId);

        Comment comment = new Comment(user,text,post);

        post.addComments(comment);

        dataPost.save(post);

        dataComment.save(comment);

    }

    public Set<Comment> getComments(Post post){

        return post.getComments();
    }



}
