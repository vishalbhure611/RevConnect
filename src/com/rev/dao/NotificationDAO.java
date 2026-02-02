package com.rev.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rev.model.Notification;
import com.rev.model.NotificationType;
import com.rev.util.DBUtil;

public class NotificationDAO {

    private static final Logger logger =
            LogManager.getLogger(NotificationDAO.class);

    public boolean createNotification(Notification notification) {

        String sql = """
                INSERT INTO notifications (user_id, type, message)
                VALUES (?, ?, ?)
                """;

        try (
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, notification.getUserId());
            ps.setString(2, notification.getType().name());
            ps.setString(3, notification.getMessage());

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            logger.error("Error creating notification", e);
            return false;
        }
    }

    public List<Notification> getNotificationsByUserId(int userId) {

        String sql = """
                SELECT * FROM notifications
                WHERE user_id = ?
                ORDER BY created_at DESC
                """;

        List<Notification> notifications = new ArrayList<>();

        try (
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Notification notification = new Notification();
                notification.setNotificationId(rs.getInt("notification_id"));
                notification.setUserId(rs.getInt("user_id"));
                notification.setType(NotificationType.valueOf(rs.getString("type")));
                notification.setMessage(rs.getString("message"));
                notification.setRead(rs.getBoolean("is_read"));
                notification.setCreatedAt(
                        rs.getTimestamp("created_at").toLocalDateTime()
                );

                notifications.add(notification);
            }

        } catch (Exception e) {
            logger.error("Error fetching notifications for userId: " + userId, e);
        }

        return notifications;
    }

    public int getUnreadCount(int userId) {

        String sql = """
                SELECT COUNT(*) FROM notifications
                WHERE user_id = ? AND is_read = FALSE
                """;

        try (
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            logger.error("Error fetching unread count", e);
        }

        return 0;
    }

    public boolean markAsRead(int notificationId) {

        String sql = """
                UPDATE notifications
                SET is_read = TRUE
                WHERE notification_id = ?
                """;

        try (
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, notificationId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            logger.error("Error marking notification as read", e);
            return false;
        }
    }
}
