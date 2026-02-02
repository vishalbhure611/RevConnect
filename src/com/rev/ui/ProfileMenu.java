package com.rev.ui;

import java.util.Scanner;

import com.rev.model.Profile;
import com.rev.service.ProfileService;

public class ProfileMenu {

    private ProfileService profileService = new ProfileService();
    private Scanner scanner = new Scanner(System.in);

    private Integer profileUserId;

    public ProfileMenu() {
        this.profileUserId = null;
    }

    public ProfileMenu(int userId) {
        this.profileUserId = userId;
    }

    public void show() {

        if (profileUserId == null) {
            showMyProfileMenu();
        } else {
            showOtherUserProfile();
        }
    }

    private void showMyProfileMenu() {

        boolean back = false;

        while (!back) {
            System.out.println("\n==== My Profile ====");
            System.out.println("1. View My Profile");
            System.out.println("2. Edit My Profile");
            System.out.println("3. Back");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> viewMyProfile();
                case 2 -> editMyProfile();
                case 3 -> back = true;
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private void viewMyProfile() {

        Profile profile = profileService.viewMyProfile();

        if (profile == null) {
            System.out.println("Profile not found");
            return;
        }

        printProfile(profile);
    }

    private void editMyProfile() {

        Profile profile = profileService.viewMyProfile();

        if (profile == null) {
            System.out.println("Profile not found");
            return;
        }

        System.out.print("Name: ");
        profile.setName(scanner.nextLine());

        System.out.print("Bio: ");
        profile.setBio(scanner.nextLine());

        System.out.print("Location: ");
        profile.setLocation(scanner.nextLine());

        System.out.print("Website: ");
        profile.setWebsite(scanner.nextLine());

        boolean updated = profileService.updateMyProfile(profile);

        System.out.println(
                updated ? "Profile updated successfully" : "Profile update failed"
        );
    }

    private void showOtherUserProfile() {

        Profile profile = profileService.getProfileByUserId(profileUserId);

        if (profile == null) {
            System.out.println("Profile not found");
            return;
        }

        System.out.println("\n==== User Profile ====");
        printProfile(profile);
    }

    private void printProfile(Profile profile) {

        System.out.println("Name: " + profile.getName());
        System.out.println("Bio: " + profile.getBio());
        System.out.println("Location: " + profile.getLocation());
        System.out.println("Website: " + profile.getWebsite());
    }
}
