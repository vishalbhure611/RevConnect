package com.rev.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rev.dao.PostDAO;
import com.rev.model.NotificationType;
import com.rev.model.Post;
import com.rev.model.User;
import com.rev.util.Session;
import java.time.LocalDateTime;

import com.rev.model.AccountType;
import com.rev.model.PostType;


public class PostService {

    private static final Logger logger =
            LogManager.getLogger(PostService.class);

    private PostDAO postDAO = new PostDAO();
    
    private NotificationService notificationService = new NotificationService();


    public boolean createPost(String content, String hashtags) {

        User currentUser = Session.getCurrentUser();

        if (currentUser == null) {
            return false;
        }

        Post post = new Post();
        post.setAuthorId(currentUser.getUserId());
        post.setContent(content);
        post.setHashtags(hashtags);

        return postDAO.createPost(post);
    }

    public List<Post> viewMyPosts() {

        User currentUser = Session.getCurrentUser();

        if (currentUser == null) {
            return List.of();
        }

        return postDAO.getPostsByAuthorId(currentUser.getUserId());
    }

    public boolean updateMyPost(int postId, String content, String hashtags) {

        User currentUser = Session.getCurrentUser();

        if (currentUser == null) {
            return false;
        }

        Post post = new Post();
        post.setPostId(postId);
        post.setAuthorId(currentUser.getUserId());
        post.setContent(content);
        post.setHashtags(hashtags);

        return postDAO.updatePost(post);
    }

    public boolean deleteMyPost(int postId) {

        User currentUser = Session.getCurrentUser();

        if (currentUser == null) {
            return false;
        }

        return postDAO.deletePost(postId, currentUser.getUserId());
    }
    
    public boolean sharePost(int originalPostId, String comment) {

        User currentUser = Session.getCurrentUser();

        if (currentUser == null) {
            return false;
        }

        Post originalPost = postDAO.getPostById(originalPostId);

        if (originalPost == null) {
            return false;
        }

        if (originalPost.getAuthorId() == currentUser.getUserId()) {
            return false;
        }

        String content = (comment == null || comment.isBlank())
                ? originalPost.getContent()
                : comment + "\n\n--- Shared Post ---\n" + originalPost.getContent();

        boolean shared = postDAO.sharePost(
                currentUser.getUserId(),
                originalPostId,
                content
        );

        if (shared) {
            notificationService.notify(
                    originalPost.getAuthorId(),
                    NotificationType.SHARE,
                    currentUser.getUsername() + " shared your post"
            );
        }


        return shared;
    }
    
    public boolean createPromotionalPost(
            String content,
            String hashtags,
            String ctaLabel,
            String ctaLink,
            LocalDateTime scheduledAt,
            boolean pinPost
    ) {

        User currentUser = Session.getCurrentUser();

        if (currentUser == null) {
            return false;
        }

        if (currentUser.getAccountType() == AccountType.PERSONAL) {
            return false;
        }

        String finalContent = content;

        if (ctaLabel != null && !ctaLabel.isBlank()) {
            finalContent += "\n\n[CTA: " + ctaLabel;
            if (ctaLink != null && !ctaLink.isBlank()) {
                finalContent += " -> " + ctaLink;
            }
            finalContent += "]";
        }

        Post post = new Post();
        post.setAuthorId(currentUser.getUserId());
        post.setContent(finalContent);
        post.setHashtags(hashtags);
        post.setPostType(PostType.PROMOTIONAL);
        post.setScheduledAt(scheduledAt);
        post.setPinned(pinPost);

        return postDAO.createPromotionalPost(post);
    }

    public boolean togglePin(int postId) {

        User currentUser = Session.getCurrentUser();

        if (currentUser == null) {
            return false;
        }

        if (currentUser.getAccountType() == AccountType.PERSONAL) {
            return false;
        }

        Post post = postDAO.getPostById(postId);

        if (post == null || post.getAuthorId() != currentUser.getUserId()) {
            return false;
        }

        boolean newStatus = !post.isPinned();
        return postDAO.updatePinStatus(postId, newStatus);
    }


}
