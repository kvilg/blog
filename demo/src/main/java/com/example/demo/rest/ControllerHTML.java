package com.example.demo.rest;

import com.example.demo.model.User;
import com.example.demo.model.UserAuth;
import com.example.demo.model.UserOut;
import com.example.demo.security.JwtTokenUtil;
import com.example.demo.servis.JwtService;
import com.example.demo.servis.UserService;
import com.solidfire.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.example.demo.model.Constants.TOKEN_PREFIX;



@Controller
public class ControllerHTML {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @Autowired
    private UserService service;

    @GetMapping(value="/login")
    public String login(){

        return "login";
    }

    @GetMapping(value="/registration")
    public String reg(){

        return "registration";
    }

    @GetMapping(value="/profile")
    public String prof() {

        if(isAuthenticated()){
            return "profile";
        }else {
            return "login";
        }
    }


    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.
                isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }

}
