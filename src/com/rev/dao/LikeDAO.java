package com.rev.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rev.util.DBUtil;

public class LikeDAO {

    private static final Logger logger =
            LogManager.getLogger(LikeDAO.class);

    public boolean likePost(int userId, int postId) {

        String sql = """
                INSERT INTO likes (user_id, post_id)
                VALUES (?, ?)
                """;

        try (
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, userId);
            ps.setInt(2, postId);
            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            logger.error("Error liking postId: " + postId, e);
            return false;
        }
    }

    public boolean unlikePost(int userId, int postId) {

        String sql = """
                DELETE FROM likes
                WHERE user_id = ? AND post_id = ?
                """;

        try (
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, userId);
            ps.setInt(2, postId);

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            logger.error("Error unliking postId: " + postId, e);
            return false;
        }
    }

    public int getLikeCount(int postId) {

        String sql = """
                SELECT COUNT(*) FROM likes
                WHERE post_id = ?
                """;

        try (
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, postId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            logger.error("Error fetching like count for postId: " + postId, e);
        }

        return 0;
    }

    public boolean hasUserLiked(int userId, int postId) {

        String sql = """
                SELECT 1 FROM likes
                WHERE user_id = ? AND post_id = ?
                """;

        try (
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, userId);
            ps.setInt(2, postId);
            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            logger.error("Error checking like for postId: " + postId, e);
            return false;
        }
    }
}
