package com.rev.ui;

import java.util.List;
import java.util.Scanner;

import com.rev.model.Notification;
import com.rev.service.NotificationService;

public class NotificationMenu {

    private NotificationService notificationService = new NotificationService();
    private Scanner scanner = new Scanner(System.in);

    public void show() {

        boolean back = false;

        while (!back) {
            System.out.println("\n==== Notifications ====");
            System.out.println("1. View Notifications");
            System.out.println("2. Mark Notification as Read");
            System.out.println("3. Back");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> viewNotifications();
                case 2 -> markAsRead();
                case 3 -> back = true;
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private void viewNotifications() {

        List<Notification> notifications =
                notificationService.viewMyNotifications();

        if (notifications.isEmpty()) {
            System.out.println("No notifications");
            return;
        }

        System.out.println("\n--- Notifications ---");
        for (Notification n : notifications) {
            System.out.println("ID: " + n.getNotificationId());
            System.out.println("Type: " + n.getType());
            System.out.println("Message: " + n.getMessage());
            System.out.println("Status: " + (n.isRead() ? "READ" : "UNREAD"));
            System.out.println("Time: " + n.getCreatedAt());
            System.out.println("---------------------");
        }
    }

    private void markAsRead() {

        System.out.print("Enter Notification ID to mark as read: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        boolean updated = notificationService.markAsRead(id);
        System.out.println(updated
                ? "Notification marked as read"
                : "Unable to update notification");
    }
}
