package com.rev.util;

import com.rev.model.User;

public class Session {

    private static User currentUser;

    // Prevent object creation
    private Session() {}

    // Set logged-in user
    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    // Get logged-in user
    public static User getCurrentUser() {
        return currentUser;
    }

    // Check if user is logged in
    public static boolean isLoggedIn() {
        return currentUser != null;
    }

    // Logout
    public static void logout() {
        currentUser = null;
    }
}
