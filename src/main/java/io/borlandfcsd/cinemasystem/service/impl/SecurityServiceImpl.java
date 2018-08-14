package io.borlandfcsd.cinemasystem.service.impl;

import io.borlandfcsd.cinemasystem.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service(value = "securityService")
public class SecurityServiceImpl implements SecurityService {
    private UserDetailsService userDetailsService;
    private AuthenticationManager authenticationManager;
    @Override
    public String findLoggedInEmail() {
        Object userDetail = SecurityContextHolder.getContext().getAuthentication().getDetails();
        if(userDetail instanceof UserDetailsService){
            return ((UserDetails) userDetail).getUsername();
        }

        return null;
    }

    @Override
    public void autoLogin(String email, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails,password,userDetails.getAuthorities());

        authenticationManager.authenticate(authenticationToken);

        if (authenticationToken.isAuthenticated()){
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

    }

    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
}
