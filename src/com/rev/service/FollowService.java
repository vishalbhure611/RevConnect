package com.rev.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rev.dao.FollowDAO;
import com.rev.model.NotificationType;
import com.rev.model.User;
import com.rev.util.Session;

public class FollowService {

    private static final Logger logger =
            LogManager.getLogger(FollowService.class);

    private FollowDAO followDAO = new FollowDAO();
    private NotificationService notificationService = new NotificationService();

    public boolean followUser(int followingId) {

        User currentUser = Session.getCurrentUser();

        if (currentUser == null) {
            return false;
        }

        if (currentUser.getUserId() == followingId) {
            return false;
        }

        boolean followed = followDAO.follow(
                currentUser.getUserId(),
                followingId
        );

        if (followed) {
            notificationService.notify(
                    followingId,
                    NotificationType.FOLLOW,
                    currentUser.getUsername() + " started following you"
            );
        }

        return followed;
    }

    public boolean unfollowUser(int followingId) {

        User currentUser = Session.getCurrentUser();

        if (currentUser == null) {
            return false;
        }

        return followDAO.unfollow(
                currentUser.getUserId(),
                followingId
        );
    }

    public List<Integer> viewFollowers() {

        User currentUser = Session.getCurrentUser();

        if (currentUser == null) {
            return List.of();
        }

        return followDAO.getFollowers(currentUser.getUserId());
    }

    public List<Integer> viewFollowing() {

        User currentUser = Session.getCurrentUser();

        if (currentUser == null) {
            return List.of();
        }

        return followDAO.getFollowing(currentUser.getUserId());
    }
}
