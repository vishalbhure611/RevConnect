package com.rev.model;

import java.time.LocalDateTime;


public class User {

    private int userId;
    private String username;
    private String email;
    private String passwordHash;
    private AccountType accountType;  
    private boolean isPrivate;
    private LocalDateTime createdAt;

    public User() {}

    public User(int userId, String username, String email,
                String passwordHash, AccountType accountType,
                boolean isPrivate, LocalDateTime createdAt) {

        this.userId = userId;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.accountType = accountType;
        this.isPrivate = isPrivate;
        this.createdAt = createdAt;
    }

   

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

