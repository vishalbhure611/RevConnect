package com.rev.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rev.model.AccountType;
import com.rev.model.User;
import com.rev.model.UserSearchResult;
import com.rev.util.DBUtil;


public class UserDAO {

    private static final Logger logger =
            LogManager.getLogger(UserDAO.class);

    // 1️⃣ Save user (Registration)
    public boolean saveUser(User user) {

        String sql = """
            INSERT INTO users
            (username, email, password_hash, account_type, is_private)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (
            Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPasswordHash());
            ps.setString(4, user.getAccountType().name());
            ps.setBoolean(5, user.isPrivate());

            int rows = ps.executeUpdate();

            logger.info("User inserted successfully: {}", user.getEmail());

            return rows > 0;

        } catch (Exception e) {
            logger.error("Error saving user: {}", user.getEmail(), e);
            return false;
        }
    }

    // 2️⃣ Find user by email (Login support)
    public User findByEmail(String email) {

        String sql = "SELECT * FROM users WHERE email = ?";

        try (
            Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPasswordHash(rs.getString("password_hash"));
                user.setAccountType(
                        AccountType.valueOf(rs.getString("account_type"))
                );
                user.setPrivate(rs.getBoolean("is_private"));
                user.setCreatedAt(
                        rs.getTimestamp("created_at").toLocalDateTime()
                );

                logger.info("User found for email: {}", email);
                return user;
            }

        } catch (Exception e) {
            logger.error("Error finding user by email: {}", email, e);
        }
        return null;
    }

    // 3️⃣ Check if email already exists
    public boolean existsByEmail(String email) {

        String sql = "SELECT 1 FROM users WHERE email = ?";

        try (
            Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            boolean exists = rs.next();
            logger.debug("Email {} exists: {}", email, exists);

            return exists;

        } catch (Exception e) {
            logger.error("Error checking email existence: {}", email, e);
            return false;
        }
    }
    
    public List<UserSearchResult> searchUsers(String keyword, int excludeUserId) {

        String sql = """
                SELECT
                    u.user_id,
                    u.username,
                    u.account_type,
                    p.name,
                    p.location
                FROM users u
                LEFT JOIN profiles p ON u.user_id = p.user_id
                WHERE
                    (LOWER(u.username) LIKE ? OR LOWER(p.name) LIKE ?)
                    AND u.user_id != ?
                """;

        List<UserSearchResult> results = new ArrayList<>();

        try (
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            String searchKey = "%" + keyword.toLowerCase() + "%";

            ps.setString(1, searchKey);
            ps.setString(2, searchKey);
            ps.setInt(3, excludeUserId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                UserSearchResult dto = new UserSearchResult();
                dto.setUserId(rs.getInt("user_id"));
                dto.setUsername(rs.getString("username"));
                dto.setAccountType(rs.getString("account_type"));
                dto.setName(rs.getString("name"));
                dto.setLocation(rs.getString("location"));

                results.add(dto);
            }

        } catch (Exception e) {
            logger.error("Error searching users", e);
        }

        return results;
    }

}
