
package com.rev.service;
import java.util.List;

import com.rev.dao.UserDAO;
import com.rev.model.User;
import com.rev.model.UserSearchResult;
import com.rev.util.Session;

public class UserService {

    private UserDAO userDAO = new UserDAO();

    public boolean registerUser(User user) {
        if (userDAO.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already registered");
        }
        return userDAO.saveUser(user);
    }

    public User login(String email, String password) {
        User user = userDAO.findByEmail(email);
        if (user == null) return null;

        if (!user.getPasswordHash().equals(password)) {
            return null;
        }
        return user;
    }
    public List<UserSearchResult> searchUsers(String keyword) {

        User currentUser = Session.getCurrentUser();

        if (currentUser == null || keyword == null || keyword.isBlank()) {
            return List.of();
        }

        return userDAO.searchUsers(keyword.trim(), currentUser.getUserId());
    }
}
