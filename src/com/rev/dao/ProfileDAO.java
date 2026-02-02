package com.rev.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rev.model.Profile;
import com.rev.util.DBUtil;

public class ProfileDAO {

    private static final Logger logger =
            LogManager.getLogger(ProfileDAO.class);

    public boolean createEmptyProfile(int userId) {

        String sql = """
                INSERT INTO profiles (user_id)
                VALUES (?)
                """;

        try (
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, userId);
            ps.executeUpdate();
            logger.info("Profile created for userId: " + userId);
            return true;

        } catch (Exception e) {
            logger.error("Error creating profile for userId: " + userId, e);
            return false;
        }
    }

    public Profile getProfileByUserId(int userId) {

        String sql = """
                SELECT * FROM profiles
                WHERE user_id = ?
                """;

        try (
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Profile profile = new Profile();
                profile.setUserId(rs.getInt("user_id"));
                profile.setName(rs.getString("name"));
                profile.setBio(rs.getString("bio"));
                profile.setProfilePicturePath(rs.getString("profile_picture_path"));
                profile.setLocation(rs.getString("location"));
                profile.setWebsite(rs.getString("website"));
                return profile;
            }

        } catch (Exception e) {
            logger.error("Error fetching profile for userId: " + userId, e);
        }
        return null;
    }

    public boolean updateProfile(Profile profile) {

        String sql = """
                UPDATE profiles
                SET name = ?, bio = ?, profile_picture_path = ?, location = ?, website = ?
                WHERE user_id = ?
                """;

        try (
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, profile.getName());
            ps.setString(2, profile.getBio());
            ps.setString(3, profile.getProfilePicturePath());
            ps.setString(4, profile.getLocation());
            ps.setString(5, profile.getWebsite());
            ps.setInt(6, profile.getUserId());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            logger.error("Error updating profile for userId: " + profile.getUserId(), e);
            return false;
        }
    }
}
