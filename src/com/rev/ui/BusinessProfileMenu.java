package com.rev.ui;

import java.util.Scanner;

import com.rev.model.BusinessProfile;
import com.rev.service.BusinessProfileService;

public class BusinessProfileMenu {

    private BusinessProfileService service = new BusinessProfileService();
    private Scanner scanner = new Scanner(System.in);

    public void show() {

        boolean back = false;

        while (!back) {
            System.out.println("\n==== Business Profile ====");
            System.out.println("1. View Business Profile");
            System.out.println("2. Edit Business Profile");
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

        BusinessProfile profile = service.viewMyBusinessProfile();

        if (profile == null) {
            System.out.println("Business profile not found");
            return;
        }

        System.out.println("\n--- Business Profile ---");
        System.out.println("Name: " + profile.getName());
        System.out.println("Bio: " + profile.getBio());
        System.out.println("Location: " + profile.getLocation());
        System.out.println("Website: " + profile.getWebsite());
        System.out.println("Industry: " + profile.getIndustry());
        System.out.println("Contact Info: " + profile.getContactInfo());
        System.out.println("Address: " + profile.getAddress());
        System.out.println("Business Hours: " + profile.getBusinessHours());
        System.out.println("External Links: " + profile.getExternalLinks());
        System.out.println("Products/Services: " + profile.getProductsServices());
    }

    private void edit() {

        BusinessProfile profile = service.viewMyBusinessProfile();

        if (profile == null) {
            System.out.println("Business profile not found");
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

        System.out.print("Industry: ");
        profile.setIndustry(scanner.nextLine());

        System.out.print("Contact Info: ");
        profile.setContactInfo(scanner.nextLine());

        System.out.print("Address: ");
        profile.setAddress(scanner.nextLine());

        System.out.print("Business Hours: ");
        profile.setBusinessHours(scanner.nextLine());

        System.out.print("External Links: ");
        profile.setExternalLinks(scanner.nextLine());

        System.out.print("Products/Services: ");
        profile.setProductsServices(scanner.nextLine());

        boolean updated = service.updateMyBusinessProfile(profile);
        System.out.println(updated ? "Profile updated successfully" : "Update failed");
    }
}
