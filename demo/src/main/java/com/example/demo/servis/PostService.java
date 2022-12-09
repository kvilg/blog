package com.example.demo.servis;

import com.example.demo.model.ModelPosts;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.repo.PostRepo;
import com.example.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

@Service
public class PostService {


    @Autowired
    private PostRepo dataPost;
    @Autowired
    private UserRepo dataUser;

    public void addPost(String img, String text, String userLogin) throws Exception {
        if(Objects.equals(img, "") || Objects.equals(text, "") ||  img ==null || text==null){
            throw new Exception("json is null");
        }

        Post post = new Post(img,text);

        User user = dataUser.getByLogin(userLogin);


        user.addPost(post);
        System.out.println(user.getPostSet().size());
        dataPost.save(post);
        dataUser.save(user);


    }

    public ModelPosts getPosts(String userLogin) throws Exception {

        if(Objects.equals(userLogin, "") ||  userLogin ==null ){
            throw new Exception("userLogin is null");
        }

        System.out.println(userLogin);

        User user = dataUser.findByLogin(userLogin);
        System.out.println(user.getLogin());
        System.out.println(user.getName());
        System.out.println(user.getPostSet().toString());
        Set<Post> postsUser = user.getPostSet();
        if(postsUser.size() == 0){
            return new ModelPosts();
        }

        ModelPosts mP =  new ModelPosts(postsUser);



        return mP;
    }





}
