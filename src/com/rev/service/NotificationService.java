package com.rev.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rev.dao.NotificationDAO;
import com.rev.model.Notification;
import com.rev.model.NotificationType;
import com.rev.model.User;
import com.rev.util.Session;

public class NotificationService {

    private static final Logger logger =
            LogManager.getLogger(NotificationService.class);

    private NotificationDAO notificationDAO = new NotificationDAO();

    public void notify(int userId, NotificationType type, String message) {

        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setType(type);
        notification.setMessage(message);

        notificationDAO.createNotification(notification);
    }

    public List<Notification> viewMyNotifications() {

        User currentUser = Session.getCurrentUser();

        if (currentUser == null) {
            return List.of();
        }

        return notificationDAO.getNotificationsByUserId(currentUser.getUserId());
    }

    public int getUnreadCount() {

        User currentUser = Session.getCurrentUser();

        if (currentUser == null) {
            return 0;
        }

        return notificationDAO.getUnreadCount(currentUser.getUserId());
    }

    public boolean markAsRead(int notificationId) {

        User currentUser = Session.getCurrentUser();

        if (currentUser == null) {
            return false;
        }

        return notificationDAO.markAsRead(notificationId);
    }
}
