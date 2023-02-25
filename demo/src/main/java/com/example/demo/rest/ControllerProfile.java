package com.example.demo.rest;

import com.example.demo.model.*;
import com.example.demo.repo.UserRepo;
import com.example.demo.security.JwtTokenUtil;
import com.example.demo.servis.CommentService;
import com.example.demo.servis.PostService;
import com.example.demo.servis.UserService;
import com.solidfire.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static com.example.demo.model.Constants.TOKEN_PREFIX;

@RestController
public class ControllerProfile {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @Autowired
    private UserService service;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserRepo userData;




    @PostMapping(value="/update/img")
    public String updateImg(@RequestBody String json,@RequestHeader("Authorization") String token) throws SQLException {

        System.out.println(token);

        System.out.println(json);

        String a = token.substring(TOKEN_PREFIX.length());
        String userLogin = jwtTokenUtil.getUsernameFromToken(a);

        User user = service.getByLogin(userLogin);



        Gson gson = new Gson();
        UserAuth gsonUser = gson.fromJson(json, UserAuth.class);



        service.updateFoto(gsonUser.img,user);


        return "ok";
    }


    @PostMapping(value="/add/post")
    public String addPost(@RequestBody String json,@RequestHeader("Authorization") String token) throws Exception {

        String a = token.substring(TOKEN_PREFIX.length());
        String userLogin = jwtTokenUtil.getUsernameFromToken(a);

        Gson gson = new Gson();
        UserAuth gsonUser = gson.fromJson(json, UserAuth.class);



        postService.addPost(gsonUser.img,gsonUser.text,userLogin);
        return "ok";
    }


    @GetMapping(value="/get/posts")
    public String getPosts(@RequestHeader("counts") Integer counts,@RequestHeader("user") String loginUser) throws Exception {

        System.out.println(loginUser+" имя пользователя");

        Gson gson = new Gson();
        ModelPosts g = postService.getPosts(loginUser,counts);
        String jsonOut = gson.toJson(g);

        return jsonOut;
    }


    @PostMapping(value="/add/comment")
    public String addComment(@RequestBody String json,
                             @RequestHeader("Authorization") String token,
                             @RequestHeader("idPost") Long idPost) throws Exception {

        String a = token.substring(TOKEN_PREFIX.length());
        String userLogin = jwtTokenUtil.getUsernameFromToken(a);

        Gson gson = new Gson();
        UserAuth gsonUser = gson.fromJson(json, UserAuth.class);


        commentService.addComment(userLogin,idPost,gsonUser.text);

        return "ok";
    }

    @GetMapping(value="/get/comment")
    public String getComment(@RequestHeader("idPost") Long idPost) throws Exception {



        Gson gson = new Gson();

        Set<Comment> commentsSet = commentService.getComments(postService.getPostById(idPost));

        List<Comment> commentList = new LinkedList<>(commentsSet);

        ModelComments mc = new ModelComments(commentList);

        return gson.toJson(mc);
    }



}
