package com.buaa.service.impl;


import com.buaa.model.entity.User;
import com.buaa.repository.UserRepository;
import com.buaa.service.AuthService;
import com.buaa.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    // 登录
    @Override
    public String login( String username, String password ) {
        System.out.println("In login");
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken( username, password );
        System.out.println("here 1");
        final Authentication authentication = authenticationManager.authenticate(upToken);
        System.out.println("here 2");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println("here 3");
        final UserDetails userDetails = userDetailsService.loadUserByUsername( username );
        System.out.println("here 4");
        final String token = jwtTokenUtil.generateToken(userDetails);
        System.out.println("token:");
        System.out.println(token);
        return token;
    }

    // 注册
    @Override
    public User register(User userToAdd ) {

        final String username = userToAdd.getUsername();
        System.out.println(userRepository.findByUsername(username));
        if( userRepository.findByUsername(username) != null) {
            return null;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String rawPassword = userToAdd.getPassword();
        userToAdd.setPassword( encoder.encode(rawPassword) );
        return userRepository.save(userToAdd);
    }
}
