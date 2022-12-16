package com.example.demo.servis;

import com.example.demo.model.JwtAuth;
import com.example.demo.model.ModelPosts;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.repo.PostRepo;
import com.example.demo.repo.UserRepo;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class PostService {

    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private PostRepo dataPost;
    @Autowired
    private UserRepo dataUser;

    public void addPost(String img, String text, String userLogin) throws Exception {
        if(Objects.equals(img, "") || Objects.equals(text, "") ||  img ==null || text==null){
            throw new Exception("json is null");
        }





        User user = dataUser.getByLogin(userLogin);

        Post post = new Post(img,text, new Timestamp(System.currentTimeMillis()),user);

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

        ModelPosts mP =  new ModelPosts((List<Post>) postsUser);



        return mP;
    }

    public ModelPosts getPosts(String userLogin,Integer counts) throws Exception {




        Session session = sessionFactory.openSession();

        User user = dataUser.findByLogin(userLogin);

        System.out.println(user.getId()+"user id");
        System.out.println(((counts*10)));
        System.out.println(((counts+1)*10));
        Integer intSql = null;
        if(counts == 0){
            intSql = 0;
        }else{
            intSql = (counts*10)+1;
        }

        session.beginTransaction();
        Criteria criteria = session.createCriteria(Post.class);
        criteria.add(Restrictions.eq("user",user));
        criteria.addOrder(Order.desc("timePost"));
        criteria.setFirstResult(intSql);
        criteria.setMaxResults(10);
        // ORDER BY time_post LIMIT "+((counts*10))+" OFFSET "+((counts+1)*10)
//        List<Post> postList = session.createSQLQuery("SELECT * FROM Post where user_id = "+user.getId() +" Order by time_post DESC LIMIT 10 OFFSET "+intSql)
//                .addEntity(Post.class).list();

        List<Post> listPost = criteria.list();

        System.out.println(listPost.size());

        ModelPosts mP =  new ModelPosts(listPost);

        return mP;
    }


    public Post getPostById(Long idPost) {
        return dataPost.getById(idPost);
    }
}
