package com.example.cheerdo.login.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUser extends User {

    private int coinCount;
    private int newLetterCount;
    private String memberName;

    public CustomUser(String username, String password
            , Collection<? extends GrantedAuthority> authorities, int coinCount, int newLetterCount, String memberName) {
        super(username, password, authorities);
        this.coinCount = coinCount;
        this.newLetterCount = newLetterCount;
        this.memberName = memberName;
    }

    public int getCoinCount() {
        return coinCount;
    }

    public int getNewLetterCount() {
        return newLetterCount;
    }

    public String getMemberName() {
        return memberName;
    }
}
