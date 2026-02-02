package com.rev.ui;

import java.util.List;
import java.util.Scanner;

import com.rev.service.FollowService;

public class FollowMenu {

    private FollowService followService = new FollowService();
    private Scanner scanner = new Scanner(System.in);

    public void show() {

        boolean back = false;

        while (!back) {
            System.out.println("\n==== Follows ====");
            System.out.println("1. Follow User");
            System.out.println("2. Unfollow User");
            System.out.println("3. View Followers");
            System.out.println("4. View Following");
            System.out.println("5. Back");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> followUser();
                case 2 -> unfollowUser();
                case 3 -> viewFollowers();
                case 4 -> viewFollowing();
                case 5 -> back = true;
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private void followUser() {

        System.out.print("Enter User ID to follow: ");
        int userId = scanner.nextInt();
        scanner.nextLine();

        boolean followed = followService.followUser(userId);
        System.out.println(followed ? "User followed successfully" : "Unable to follow user");
    }

    private void unfollowUser() {

        System.out.print("Enter User ID to unfollow: ");
        int userId = scanner.nextInt();
        scanner.nextLine();

        boolean unfollowed = followService.unfollowUser(userId);
        System.out.println(unfollowed ? "User unfollowed successfully" : "Unable to unfollow user");
    }

    private void viewFollowers() {

        List<Integer> followers = followService.viewFollowers();

        if (followers.isEmpty()) {
            System.out.println("No followers found");
            return;
        }

        System.out.println("\n--- Followers ---");
        for (int userId : followers) {
            System.out.println("Follower User ID: " + userId);
        }
    }

    private void viewFollowing() {

        List<Integer> following = followService.viewFollowing();

        if (following.isEmpty()) {
            System.out.println("Not following anyone");
            return;
        }

        System.out.println("\n--- Following ---");
        for (int userId : following) {
            System.out.println("Following User ID: " + userId);
        }
    }
}
