package com.rev.ui;

import java.util.List;
import java.util.Scanner;

import com.rev.model.Post;
import com.rev.service.CommentService;
import com.rev.service.FeedService;
import com.rev.service.LikeService;

public class FeedMenu {

    private FeedService feedService = new FeedService();
    private LikeService likeService = new LikeService();
    private CommentService commentService = new CommentService();
    private Scanner scanner = new Scanner(System.in);

    public void show() {

        boolean back = false;

        while (!back) {
            System.out.println("\n==== Feed ====");
            System.out.println("1. View Feed");
            System.out.println("2. Back");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> viewFeed();
                case 2 -> back = true;
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private void viewFeed() {

        List<Post> feed = feedService.getFeed();

        if (feed.isEmpty()) {
            System.out.println("Your feed is empty");
            return;
        }

        System.out.println("\n--- Feed ---");
        for (Post post : feed) {
            System.out.println("Post ID: " + post.getPostId());
            System.out.println("Author ID: " + post.getAuthorId());
            System.out.println("Content: " + post.getContent());
            System.out.println("Hashtags: " + post.getHashtags());
            System.out.println("Likes: " + likeService.getLikeCount(post.getPostId()));
            System.out.println("Comments: " + commentService.viewComments(post.getPostId()).size());
            System.out.println("-----------------------------");
        }
    }
}
