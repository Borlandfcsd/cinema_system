package io.borlandfcsd.cinemasystem.service;

public interface SecurityService {
    String findLoggedInEmail();
    void autoLogin(String email, String password);
}
