package com.rev.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rev.model.Comment;
import com.rev.util.DBUtil;

public class CommentDAO {

    private static final Logger logger =
            LogManager.getLogger(CommentDAO.class);

    public boolean addComment(Comment comment) {

        String sql = """
                INSERT INTO comments (post_id, author_id, content)
                VALUES (?, ?, ?)
                """;

        try (
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, comment.getPostId());
            ps.setInt(2, comment.getAuthorId());
            ps.setString(3, comment.getContent());

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            logger.error("Error adding comment", e);
            return false;
        }
    }

    public List<Comment> getCommentsByPostId(int postId) {

        String sql = """
                SELECT * FROM comments
                WHERE post_id = ?
                ORDER BY created_at ASC
                """;

        List<Comment> comments = new ArrayList<>();

        try (
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, postId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Comment comment = new Comment();
                comment.setCommentId(rs.getInt("comment_id"));
                comment.setPostId(rs.getInt("post_id"));
                comment.setAuthorId(rs.getInt("author_id"));
                comment.setContent(rs.getString("content"));
                comment.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

                comments.add(comment);
            }

        } catch (Exception e) {
            logger.error("Error fetching comments for postId: " + postId, e);
        }

        return comments;
    }

    public boolean deleteComment(int commentId, int authorId) {

        String sql = """
                DELETE FROM comments
                WHERE comment_id = ? AND author_id = ?
                """;

        try (
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, commentId);
            ps.setInt(2, authorId);

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            logger.error("Error deleting commentId: " + commentId, e);
            return false;
        }
    }
}
