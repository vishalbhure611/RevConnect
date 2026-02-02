package com.rev.ui;

import java.util.List;
import java.util.Scanner;

import com.rev.service.ConnectionService;

public class ConnectionMenu {

    private ConnectionService connectionService = new ConnectionService();
    private Scanner scanner = new Scanner(System.in);

    public void show() {

        boolean back = false;

        while (!back) {
            System.out.println("\n==== Connections ====");
            System.out.println("1. Send Connection Request");
            System.out.println("2. View Incoming Requests");
            System.out.println("3. Accept Request");
            System.out.println("4. Reject Request");
            System.out.println("5. View My Connections");
            System.out.println("6. Remove Connection");
            System.out.println("7. Back");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> sendRequest();
                case 2 -> viewIncomingRequests();
                case 3 -> acceptRequest();
                case 4 -> rejectRequest();
                case 5 -> viewConnections();
                case 6 -> removeConnection();
                case 7 -> back = true;
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private void sendRequest() {

        System.out.print("Enter User ID to connect with: ");
        int receiverId = scanner.nextInt();
        scanner.nextLine();

        boolean sent = connectionService.sendConnectionRequest(receiverId);
        System.out.println(sent ? "Connection request sent" : "Unable to send request");
    }

    private void viewIncomingRequests() {

        List<Integer> requests = connectionService.viewIncomingRequests();

        if (requests.isEmpty()) {
            System.out.println("No incoming requests");
            return;
        }

        System.out.println("\n--- Incoming Requests ---");
        for (int userId : requests) {
            System.out.println("Requester User ID: " + userId);
        }
    }

    private void acceptRequest() {

        System.out.print("Enter Requester User ID to accept: ");
        int requesterId = scanner.nextInt();
        scanner.nextLine();

        boolean accepted = connectionService.acceptRequest(requesterId);
        System.out.println(accepted ? "Connection accepted" : "Unable to accept request");
    }

    private void rejectRequest() {

        System.out.print("Enter Requester User ID to reject: ");
        int requesterId = scanner.nextInt();
        scanner.nextLine();

        boolean rejected = connectionService.rejectRequest(requesterId);
        System.out.println(rejected ? "Connection rejected" : "Unable to reject request");
    }

    private void viewConnections() {

        List<Integer> connections = connectionService.viewMyConnections();

        if (connections.isEmpty()) {
            System.out.println("No connections found");
            return;
        }

        System.out.println("\n--- My Connections ---");
        for (int userId : connections) {
            System.out.println("Connected User ID: " + userId);
        }
    }

    private void removeConnection() {

        System.out.print("Enter User ID to remove connection: ");
        int userId = scanner.nextInt();
        scanner.nextLine();

        boolean removed = connectionService.removeConnection(userId);
        System.out.println(removed ? "Connection removed" : "Unable to remove connection");
    }
}
