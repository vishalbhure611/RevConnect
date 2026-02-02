package com.rev.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rev.util.DBUtil;

public class FollowDAO {

    private static final Logger logger =
            LogManager.getLogger(FollowDAO.class);

    public boolean follow(int followerId, int followingId) {

        String sql = """
                INSERT INTO follows (follower_id, following_id)
                VALUES (?, ?)
                """;

        try (
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, followerId);
            ps.setInt(2, followingId);
            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            logger.error("Error following user", e);
            return false;
        }
    }

    public boolean unfollow(int followerId, int followingId) {

        String sql = """
                DELETE FROM follows
                WHERE follower_id = ? AND following_id = ?
                """;

        try (
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, followerId);
            ps.setInt(2, followingId);

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            logger.error("Error unfollowing user", e);
            return false;
        }
    }

    public List<Integer> getFollowers(int userId) {

        String sql = """
                SELECT follower_id FROM follows
                WHERE following_id = ?
                """;

        List<Integer> followers = new ArrayList<>();

        try (
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                followers.add(rs.getInt("follower_id"));
            }

        } catch (Exception e) {
            logger.error("Error fetching followers", e);
        }

        return followers;
    }

    public List<Integer> getFollowing(int userId) {

        String sql = """
                SELECT following_id FROM follows
                WHERE follower_id = ?
                """;

        List<Integer> following = new ArrayList<>();

        try (
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                following.add(rs.getInt("following_id"));
            }

        } catch (Exception e) {
            logger.error("Error fetching following list", e);
        }

        return following;
    }
}
