package com.rev.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rev.dao.UserDAO;
import com.rev.model.User;
import com.rev.util.PasswordUtil;
import com.rev.util.Session;

public class AuthService {

    private static final Logger logger =
            LogManager.getLogger(AuthService.class);

    private UserDAO userDAO = new UserDAO();
    private ProfileService profileService = new ProfileService();

    public boolean register(User user) {

        if (userDAO.existsByEmail(user.getEmail())) {
            logger.warn("Registration failed - Email already exists: " + user.getEmail());
            return false;
        }

        String hashedPassword = PasswordUtil.hashPassword(user.getPasswordHash());
        user.setPasswordHash(hashedPassword);

        boolean saved = userDAO.saveUser(user);

        if (!saved) {
            return false;
        }

        User savedUser = userDAO.findByEmail(user.getEmail());

        if (savedUser != null) {
            profileService.createEmptyProfile(savedUser.getUserId());
            logger.info("User registered with profile created: " + user.getEmail());
        }

        return true;
    }

    public User login(String email, String password) {

        User user = userDAO.findByEmail(email);

        if (user == null) {
            logger.warn("Login failed - User not found: " + email);
            return null;
        }

        if (!PasswordUtil.verifyPassword(password, user.getPasswordHash())) {
            logger.warn("Login failed - Invalid password for: " + email);
            return null;
        }

        Session.setCurrentUser(user);
        logger.info("User logged in: " + email);

        return user;
    }

    public void logout() {
        Session.logout();
        logger.info("User logged out");
    }
}
