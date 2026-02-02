package com.rev.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rev.dao.ConnectionDAO;
import com.rev.model.ConnectionStatus;
import com.rev.model.NotificationType;
import com.rev.model.User;
import com.rev.util.Session;

public class ConnectionService {

    private static final Logger logger =
            LogManager.getLogger(ConnectionService.class);

    private ConnectionDAO connectionDAO = new ConnectionDAO();
    private NotificationService notificationService = new NotificationService();

    public boolean sendConnectionRequest(int receiverId) {

        User currentUser = Session.getCurrentUser();

        if (currentUser == null) {
            return false;
        }

        if (currentUser.getUserId() == receiverId) {
            return false;
        }

        boolean sent = connectionDAO.sendRequest(
                currentUser.getUserId(),
                receiverId
        );

        if (sent) {
            notificationService.notify(
                    receiverId,
                    NotificationType.CONNECTION_REQUEST,
                    currentUser.getUsername() + " sent you a connection request"
            );
        }

        return sent;
    }

    public List<Integer> viewIncomingRequests() {

        User currentUser = Session.getCurrentUser();

        if (currentUser == null) {
            return List.of();
        }

        return connectionDAO.getIncomingRequests(currentUser.getUserId());
    }

    public boolean acceptRequest(int requesterId) {

        User currentUser = Session.getCurrentUser();

        if (currentUser == null) {
            return false;
        }

        boolean accepted = connectionDAO.updateStatus(
                requesterId,
                currentUser.getUserId(),
                ConnectionStatus.ACCEPTED
        );

        if (accepted) {
            notificationService.notify(
                    requesterId,
                    NotificationType.CONNECTION_ACCEPTED,
                    currentUser.getUsername() + " accepted your connection request"
            );
        }

        return accepted;
    }

    public boolean rejectRequest(int requesterId) {

        User currentUser = Session.getCurrentUser();

        if (currentUser == null) {
            return false;
        }

        return connectionDAO.updateStatus(
                requesterId,
                currentUser.getUserId(),
                ConnectionStatus.REJECTED
        );
    }

    public List<Integer> viewMyConnections() {

        User currentUser = Session.getCurrentUser();

        if (currentUser == null) {
            return List.of();
        }

        return connectionDAO.getConnections(currentUser.getUserId());
    }

    public boolean removeConnection(int otherUserId) {

        User currentUser = Session.getCurrentUser();

        if (currentUser == null) {
            return false;
        }

        return connectionDAO.removeConnection(
                currentUser.getUserId(),
                otherUserId
        );
    }
}
