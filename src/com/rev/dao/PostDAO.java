package com.rev.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rev.model.Post;
import com.rev.model.PostType;
import com.rev.util.DBUtil;

public class PostDAO {

    private static final Logger logger =
            LogManager.getLogger(PostDAO.class);

    public boolean createPost(Post post) {

        String sql = """
                INSERT INTO posts (author_id, content, hashtags, post_type)
                VALUES (?, ?, ?, ?)
                """;

        try (
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, post.getAuthorId());
            ps.setString(2, post.getContent());
            ps.setString(3, post.getHashtags());
            ps.setString(4, PostType.NORMAL.name());

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            logger.error("Error creating post", e);
            return false;
        }
    }

    public List<Post> getPostsByAuthorId(int authorId) {

        String sql = """
                SELECT * FROM posts
                WHERE author_id = ?
                ORDER BY created_at DESC
                """;

        List<Post> posts = new ArrayList<>();

        try (
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, authorId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Post post = mapPost(rs);
                posts.add(post);
            }

        } catch (Exception e) {
            logger.error("Error fetching posts for userId: " + authorId, e);
        }

        return posts;
    }

    public boolean updatePost(Post post) {

        String sql = """
                UPDATE posts
                SET content = ?, hashtags = ?
                WHERE post_id = ? AND author_id = ?
                """;

        try (
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, post.getContent());
            ps.setString(2, post.getHashtags());
            ps.setInt(3, post.getPostId());
            ps.setInt(4, post.getAuthorId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            logger.error("Error updating postId: " + post.getPostId(), e);
            return false;
        }
    }

    public boolean deletePost(int postId, int authorId) {

        String sql = """
                DELETE FROM posts
                WHERE post_id = ? AND author_id = ?
                """;

        try (
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, postId);
            ps.setInt(2, authorId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            logger.error("Error deleting postId: " + postId, e);
            return false;
        }
    }

    public List<Post> getFeedPosts(List<Integer> authorIds) {

        if (authorIds == null || authorIds.isEmpty()) {
            return List.of();
        }

        String placeholders = authorIds.stream()
                .map(id -> "?")
                .reduce((a, b) -> a + "," + b)
                .orElse("");

        String sql =
        	    "SELECT * FROM posts " +
        	    "WHERE author_id IN (" + placeholders + ") " +
        	    "AND (scheduled_at IS NULL OR scheduled_at <= NOW()) " +
        	    "ORDER BY is_pinned DESC, created_at DESC";



        List<Post> posts = new ArrayList<>();

        try (
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            for (int i = 0; i < authorIds.size(); i++) {
                ps.setInt(i + 1, authorIds.get(i));
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Post post = mapPost(rs);
                posts.add(post);
            }

        } catch (Exception e) {
            logger.error("Error fetching feed posts", e);
        }

        return posts;
    }

    private Post mapPost(ResultSet rs) throws Exception {

        Post post = new Post();
        post.setPostId(rs.getInt("post_id"));
        post.setAuthorId(rs.getInt("author_id"));
        post.setContent(rs.getString("content"));
        post.setHashtags(rs.getString("hashtags"));
        post.setPostType(PostType.valueOf(rs.getString("post_type")));
        post.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return post;
    }
    public Post getPostById(int postId) {

        String sql = "SELECT * FROM posts WHERE post_id = ?";

        try (
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, postId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapPost(rs);
            }

        } catch (Exception e) {
            logger.error("Error fetching post by id", e);
        }

        return null;
    }
    
    public boolean sharePost(int authorId, int originalPostId, String content) {

        String sql = """
                INSERT INTO posts (author_id, content, post_type, original_post_id)
                VALUES (?, ?, ?, ?)
                """;

        try (
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, authorId);
            ps.setString(2, content);
            ps.setString(3, PostType.SHARED.name());
            ps.setInt(4, originalPostId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            logger.error("Error sharing post", e);
            return false;
        }
    }
    
    public boolean createPromotionalPost(Post post) {

        String sql = """
                INSERT INTO posts
                (author_id, content, hashtags, post_type, scheduled_at, is_pinned)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, post.getAuthorId());
            ps.setString(2, post.getContent());
            ps.setString(3, post.getHashtags());
            ps.setString(4, post.getPostType().name());
            ps.setObject(5, post.getScheduledAt());
            ps.setBoolean(6, post.isPinned());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            logger.error("Error creating promotional post", e);
            return false;
        }
    }

    public boolean updatePinStatus(int postId, boolean pinned) {

        String sql = """
                UPDATE posts
                SET is_pinned = ?
                WHERE post_id = ?
                """;

        try (
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setBoolean(1, pinned);
            ps.setInt(2, postId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            logger.error("Error updating pin status", e);
            return false;
        }
    }




}
