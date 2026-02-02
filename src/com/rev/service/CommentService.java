package com.rev.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rev.dao.CommentDAO;
import com.rev.dao.PostDAO;
import com.rev.model.Comment;
import com.rev.model.NotificationType;
import com.rev.model.Post;
import com.rev.model.User;
import com.rev.util.Session;

public class CommentService {

    private static final Logger logger =
            LogManager.getLogger(CommentService.class);

    private CommentDAO commentDAO = new CommentDAO();
    private PostDAO postDAO = new PostDAO();
    private NotificationService notificationService = new NotificationService();

    public boolean addComment(int postId, String content) {

        User currentUser = Session.getCurrentUser();

        if (currentUser == null) {
            return false;
        }

        Comment comment = new Comment();
        comment.setPostId(postId);
        comment.setAuthorId(currentUser.getUserId());
        comment.setContent(content);

        boolean added = commentDAO.addComment(comment);

        if (added) {
            Post post = postDAO.getPostById(postId);

            if (post != null && post.getAuthorId() != currentUser.getUserId()) {
                notificationService.notify(
                        post.getAuthorId(),
                        NotificationType.COMMENT,
                        currentUser.getUsername() + " commented on your post"
                );
            }
        }

        return added;
    }

    public List<Comment> viewComments(int postId) {
        return commentDAO.getCommentsByPostId(postId);
    }

    public boolean deleteMyComment(int commentId) {

        User currentUser = Session.getCurrentUser();

        if (currentUser == null) {
            return false;
        }

        return commentDAO.deleteComment(commentId, currentUser.getUserId());
    }
}
