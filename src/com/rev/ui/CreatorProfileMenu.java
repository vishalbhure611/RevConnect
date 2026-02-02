package com.rev.ui;

import java.util.Scanner;

import com.rev.model.CreatorProfile;
import com.rev.service.CreatorProfileService;

public class CreatorProfileMenu {

    private CreatorProfileService service = new CreatorProfileService();
    private Scanner scanner = new Scanner(System.in);

    public void show() {

        boolean back = false;

        while (!back) {
            System.out.println("\n==== Creator Profile ====");
            System.out.println("1. View Creator Profile");
            System.out.println("2. Edit Creator Profile");
            System.out.println("3. Back");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> view();
                case 2 -> edit();
                case 3 -> back = true;
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private void view() {

        CreatorProfile profile = service.viewMyCreatorProfile();

        if (profile == null) {
            System.out.println("Creator profile not found");
            return;
        }

        System.out.println("\n--- Creator Profile ---");
        System.out.println("Name: " + profile.getName());
        System.out.println("Bio: " + profile.getBio());
        System.out.println("Location: " + profile.getLocation());
        System.out.println("Website: " + profile.getWebsite());
        System.out.println("Category: " + profile.getCategory());
        System.out.println("Contact Info: " + profile.getContactInfo());
        System.out.println("External Links: " + profile.getExternalLinks());
    }

    private void edit() {

        CreatorProfile profile = service.viewMyCreatorProfile();

        if (profile == null) {
            System.out.println("Creator profile not found");
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

        System.out.print("Category: ");
        profile.setCategory(scanner.nextLine());

        System.out.print("Contact Info: ");
        profile.setContactInfo(scanner.nextLine());

        System.out.print("External Links: ");
        profile.setExternalLinks(scanner.nextLine());

        boolean updated = service.updateMyCreatorProfile(profile);
        System.out.println(updated ? "Profile updated successfully" : "Update failed");
    }
}
