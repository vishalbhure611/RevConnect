package com.rev.ui;

import java.time.LocalDateTime;

import com.rev.dao.UserDAO;
import com.rev.model.AccountType;
import com.rev.model.User;

public class UserDAOManualTest {

    public static void main(String[] args) {

        UserDAO userDAO = new UserDAO();

        // 1️⃣ Create test user
        User user = new User();
        user.setUsername("vishal");
        user.setEmail("vishal@test.com");
        user.setPasswordHash("test123"); // plain for now
        user.setAccountType(AccountType.PERSONAL);
        user.setPrivate(false);
        user.setCreatedAt(LocalDateTime.now());

        // 2️⃣ Save user
        boolean saved = userDAO.saveUser(user);
        System.out.println("User saved: " + saved);

        // 3️⃣ Fetch user by email
        User fetchedUser = userDAO.findByEmail("vishal@test.com");

        if (fetchedUser != null) {
            System.out.println("User found:");
            System.out.println("ID: " + fetchedUser.getUserId());
            System.out.println("Username: " + fetchedUser.getUsername());
            System.out.println("Email: " + fetchedUser.getEmail());
            System.out.println("Account Type: " + fetchedUser.getAccountType());
        } else {
            System.out.println("User not found");
        }

        // 4️⃣ Check duplicate email
        boolean exists = userDAO.existsByEmail("vishal@test.com");
        System.out.println("Email exists: " + exists);
    }
}

