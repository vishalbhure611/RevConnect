package com.rev.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rev.model.ConnectionStatus;
import com.rev.util.DBUtil;

public class ConnectionDAO {

    private static final Logger logger =
            LogManager.getLogger(ConnectionDAO.class);

    public boolean sendRequest(int requesterId, int receiverId) {

        String sql = """
                INSERT INTO connections (requester_id, receiver_id, status)
                VALUES (?, ?, ?)
                """;

        try (
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, requesterId);
            ps.setInt(2, receiverId);
            ps.setString(3, ConnectionStatus.PENDING.name());

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            logger.error("Error sending connection request", e);
            return false;
        }
    }

    public boolean updateStatus(int requesterId, int receiverId, ConnectionStatus status) {

        String sql = """
                UPDATE connections
                SET status = ?
                WHERE requester_id = ? AND receiver_id = ?
                """;

        try (
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, status.name());
            ps.setInt(2, requesterId);
            ps.setInt(3, receiverId);

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            logger.error("Error updating connection status", e);
            return false;
        }
    }

    public List<Integer> getIncomingRequests(int userId) {

        String sql = """
                SELECT requester_id FROM connections
                WHERE receiver_id = ? AND status = 'PENDING'
                """;

        List<Integer> requesters = new ArrayList<>();

        try (
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                requesters.add(rs.getInt("requester_id"));
            }

        } catch (Exception e) {
            logger.error("Error fetching incoming requests", e);
        }

        return requesters;
    }

    public List<Integer> getConnections(int userId) {

        String sql = """
                SELECT requester_id, receiver_id FROM connections
                WHERE status = 'ACCEPTED'
                AND (requester_id = ? OR receiver_id = ?)
                """;

        List<Integer> connections = new ArrayList<>();

        try (
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, userId);
            ps.setInt(2, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int requester = rs.getInt("requester_id");
                int receiver = rs.getInt("receiver_id");

                connections.add(requester == userId ? receiver : requester);
            }

        } catch (Exception e) {
            logger.error("Error fetching connections", e);
        }

        return connections;
    }

    public boolean removeConnection(int userId1, int userId2) {

        String sql = """
                DELETE FROM connections
                WHERE status = 'ACCEPTED'
                AND (
                    (requester_id = ? AND receiver_id = ?)
                    OR
                    (requester_id = ? AND receiver_id = ?)
                )
                """;

        try (
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, userId1);
            ps.setInt(2, userId2);
            ps.setInt(3, userId2);
            ps.setInt(4, userId1);

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            logger.error("Error removing connection", e);
            return false;
        }
    }
}
