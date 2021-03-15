package com.languages.learningapi.controllers;

import com.languages.learningapi.models.Login;
import com.languages.learningapi.models.RolesEnum;
import com.languages.learningapi.security.JwtTokenProvider;
import com.languages.learningapi.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("auth")
public class AuthCtrl {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserDetailsServiceImpl usersService;

    @RequestMapping(value = "signin", method = RequestMethod.POST)
    public ResponseEntity signin(@RequestBody Login login) {
        try {
            UserDetails users = this.usersService.loadUserByUsername(login.getUsername());
            if(users != null && login.getPassword().equals(users.getPassword())){
                //authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(users.getUsername(), users.getPassword()));
                String token = jwtTokenProvider.createToken(users.getUsername(), RolesEnum.ROLE_ADMIN);
                Map<Object, Object> model = new HashMap<>();
                model.put("username", users.getUsername());
                model.put("token", token);
                model.put("roles", users.getAuthorities());
                return new ResponseEntity(model, HttpStatus.OK);
            }else{
                return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
            }
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        } catch (NullPointerException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }
}
