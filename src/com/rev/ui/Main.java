package com.rev.ui;

import java.util.Scanner;

import com.rev.model.AccountType;
import com.rev.model.User;
import com.rev.service.AuthService;
import com.rev.service.NotificationService;
import com.rev.util.Session;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static AuthService authService = new AuthService();

    public static void main(String[] args) {

        boolean running = true;

        while (running) {

            if (!Session.isLoggedIn()) {
                showAuthMenu();
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> register();
                    case 2 -> login();
                    case 3 -> {
                        System.out.println("Exiting RevConnect...");
                        running = false;
                    }
                    default -> System.out.println("Invalid choice");
                }

            } else {
                showDashboard();
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                case 1 -> new ProfileMenu().show();
                case 2 -> new PostMenu().show();
                case 3 -> new ConnectionMenu().show();
                case 4 -> new FollowMenu().show();
                case 5 -> new FeedMenu().show();
                case 6 -> new NotificationMenu().show();
                case 7 -> new UserSearchMenu().show();
                case 8 ->  new EnhancedProfileMenu().show();
                case 9 -> {
                    authService.logout();
                    System.out.println("Logged out successfully");
                }
                case 10 -> {
                    System.out.println("Goodbye!");
                    running = false;
                }




                    default -> System.out.println("Invalid choice");
                }
            }
        }
        scanner.close();
    }

    private static void showAuthMenu() {
        System.out.println("\n==== RevConnect ====");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.print("Choose option: ");
    }

    private static void showDashboard() {

        NotificationService notificationService = new NotificationService();
        int unread = notificationService.getUnreadCount();
        System.out.println("\n==== Dashboard ====");
        System.out.println("Welcome " + Session.getCurrentUser().getUsername());
        System.out.println("1. My Profile");
        System.out.println("2. Posts");
        System.out.println("3. Connections");
        System.out.println("4. Follows");
        System.out.println("5. Feed");
        System.out.println("6. Notifications");
        System.out.println("7. Search Users");
        System.out.println("8. Enhanced Profile");
        System.out.println("9. Logout");
        System.out.println("10. Exit");
        System.out.print("Choose option: ");
    }

    private static void register() {
        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        System.out.print("Account Type (PERSONAL / CREATOR / BUSINESS): ");
        String type = scanner.nextLine();

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPasswordHash(password);
        user.setAccountType(AccountType.valueOf(type.toUpperCase()));
        user.setPrivate(false);

        boolean success = authService.register(user);

        if (success) {
            System.out.println("Registration successful!");
        } else {
            System.out.println("Registration failed!");
        }
    }

    private static void login() {
        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        User user = authService.login(email, password);

        if (user != null) {
            System.out.println("Login successful. Welcome " + user.getUsername());
        } else {
            System.out.println("Invalid email or password");
        }
    }
}
