package com.citc.cso.service;

public interface SecurityService {
    boolean isAuthenticated();
    void autoLogin(String username, String password);
}

