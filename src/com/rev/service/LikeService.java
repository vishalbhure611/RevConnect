package com.rev.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rev.dao.LikeDAO;
import com.rev.dao.PostDAO;
import com.rev.model.NotificationType;
import com.rev.model.Post;
import com.rev.model.User;
import com.rev.util.Session;

public class LikeService {

    private static final Logger logger =
            LogManager.getLogger(LikeService.class);

    private LikeDAO likeDAO = new LikeDAO();
    private PostDAO postDAO = new PostDAO();
    private NotificationService notificationService = new NotificationService();

    public boolean likePost(int postId) {

        User currentUser = Session.getCurrentUser();

        if (currentUser == null) {
            return false;
        }

        if (likeDAO.hasUserLiked(currentUser.getUserId(), postId)) {
            return false;
        }

        boolean liked = likeDAO.likePost(currentUser.getUserId(), postId);

        if (liked) {
            Post post = postDAO.getPostById(postId);

            if (post != null && post.getAuthorId() != currentUser.getUserId()) {
                notificationService.notify(
                        post.getAuthorId(),
                        NotificationType.LIKE,
                        currentUser.getUsername() + " liked your post"
                );
            }
        }

        return liked;
    }

    public boolean unlikePost(int postId) {

        User currentUser = Session.getCurrentUser();

        if (currentUser == null) {
            return false;
        }

        return likeDAO.unlikePost(currentUser.getUserId(), postId);
    }

    public int getLikeCount(int postId) {
        return likeDAO.getLikeCount(postId);
    }

    public boolean hasUserLiked(int postId) {

        User currentUser = Session.getCurrentUser();

        if (currentUser == null) {
            return false;
        }

        return likeDAO.hasUserLiked(currentUser.getUserId(), postId);
    }
}
