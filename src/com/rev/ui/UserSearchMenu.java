package com.rev.ui;

import java.util.List;
import java.util.Scanner;

import com.rev.model.UserSearchResult;
import com.rev.service.ConnectionService;
import com.rev.service.FollowService;
import com.rev.service.UserService;

public class UserSearchMenu {

    private UserService userService = new UserService();
    private ConnectionService connectionService = new ConnectionService();
    private FollowService followService = new FollowService();
    private Scanner scanner = new Scanner(System.in);

    public void show() {

        boolean back = false;

        while (!back) {
            System.out.println("\n==== User Search ====");
            System.out.println("1. Search Users");
            System.out.println("2. Back");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> searchUsers();
                case 2 -> back = true;
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private void searchUsers() {

        System.out.print("Enter name or username to search: ");
        String keyword = scanner.nextLine();

        List<UserSearchResult> results = userService.searchUsers(keyword);

        if (results.isEmpty()) {
            System.out.println("No users found");
            return;
        }

        System.out.println("\n--- Search Results ---");
        for (UserSearchResult u : results) {
            System.out.println("User ID: " + u.getUserId());
            System.out.println("Username: " + u.getUsername());
            System.out.println("Name: " + u.getName());
            System.out.println("Account Type: " + u.getAccountType());
            System.out.println("Location: " + u.getLocation());
            System.out.println("---------------------");
        }

        System.out.println("1. View Profile");
        System.out.println("2. Send Connection Request");
        System.out.println("3. Follow User");
        System.out.println("4. Back");
        System.out.print("Choose option: ");

        int action = scanner.nextInt();
        scanner.nextLine();

        switch (action) {
            case 1 -> viewProfile();
            case 2 -> sendConnectionRequest();
            case 3 -> followUser();
            default -> {}
        }
    }

    private void viewProfile() {

        System.out.print("Enter User ID to view profile: ");
        int userId = scanner.nextInt();
        scanner.nextLine();

        new ProfileMenu(userId).show();
    }

    private void sendConnectionRequest() {

        System.out.print("Enter User ID to connect: ");
        int userId = scanner.nextInt();
        scanner.nextLine();

        boolean sent = connectionService.sendConnectionRequest(userId);
        System.out.println(sent
                ? "Connection request sent"
                : "Unable to send request");
    }

    private void followUser() {

        System.out.print("Enter User ID to follow: ");
        int userId = scanner.nextInt();
        scanner.nextLine();

        boolean followed = followService.followUser(userId);
        System.out.println(followed
                ? "User followed"
                : "Unable to follow user");
    }
}
