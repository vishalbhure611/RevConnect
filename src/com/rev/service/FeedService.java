package com.rev.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rev.dao.ConnectionDAO;
import com.rev.dao.FollowDAO;
import com.rev.dao.PostDAO;
import com.rev.model.Post;
import com.rev.model.User;
import com.rev.util.Session;

public class FeedService {

    private static final Logger logger =
            LogManager.getLogger(FeedService.class);

    private PostDAO postDAO = new PostDAO();
    private ConnectionDAO connectionDAO = new ConnectionDAO();
    private FollowDAO followDAO = new FollowDAO();

    public List<Post> getFeed() {

        User currentUser = Session.getCurrentUser();

        if (currentUser == null) {
            return List.of();
        }

        Set<Integer> authorIds = new HashSet<>();

        int userId = currentUser.getUserId();

        authorIds.add(userId);

        authorIds.addAll(connectionDAO.getConnections(userId));

        authorIds.addAll(followDAO.getFollowing(userId));

        return postDAO.getFeedPosts(new ArrayList<>(authorIds));
    }
}
