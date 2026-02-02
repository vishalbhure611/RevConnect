package com.rev.ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import com.rev.model.AccountType;
import com.rev.model.Comment;
import com.rev.model.Post;
import com.rev.model.User;
import com.rev.service.CommentService;
import com.rev.service.LikeService;
import com.rev.service.PostService;
import com.rev.util.Session;

public class PostMenu {

    private PostService postService = new PostService();
    private LikeService likeService = new LikeService();
    private CommentService commentService = new CommentService();
    private Scanner scanner = new Scanner(System.in);

    public void show() {

        boolean back = false;

        while (!back) {

            User user = Session.getCurrentUser();

            System.out.println("\n==== Posts ====");
            System.out.println("1. Create Normal Post");

            if (user.getAccountType() != AccountType.PERSONAL) {
                System.out.println("2. Create Promotional Post");
                System.out.println("3. Pin / Unpin My Post");
                System.out.println("4. Share a Post");
                System.out.println("5. View My Posts");
                System.out.println("6. Like a Post");
                System.out.println("7. Unlike a Post");
                System.out.println("8. View Like Count");
                System.out.println("9. Add Comment");
                System.out.println("10. View Comments");
                System.out.println("11. Delete My Comment");
                System.out.println("12. Edit My Post");
                System.out.println("13. Delete My Post");
                System.out.println("14. Back");
            } else {
                System.out.println("2. Share a Post");
                System.out.println("3. View My Posts");
                System.out.println("4. Like a Post");
                System.out.println("5. Unlike a Post");
                System.out.println("6. View Like Count");
                System.out.println("7. Add Comment");
                System.out.println("8. View Comments");
                System.out.println("9. Delete My Comment");
                System.out.println("10. Edit My Post");
                System.out.println("11. Delete My Post");
                System.out.println("12. Back");
            }

            System.out.print("Choose option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (user.getAccountType() != AccountType.PERSONAL) {
                switch (choice) {
                    case 1 -> createPost();
                    case 2 -> createPromotionalPost();
                    case 3 -> pinPost();
                    case 4 -> sharePost();
                    case 5 -> viewMyPosts();
                    case 6 -> likePost();
                    case 7 -> unlikePost();
                    case 8 -> viewLikeCount();
                    case 9 -> addComment();
                    case 10 -> viewComments();
                    case 11 -> deleteMyComment();
                    case 12 -> editPost();
                    case 13 -> deletePost();
                    case 14 -> back = true;
                    default -> System.out.println("Invalid choice");
                }
            } else {
                switch (choice) {
                    case 1 -> createPost();
                    case 2 -> sharePost();
                    case 3 -> viewMyPosts();
                    case 4 -> likePost();
                    case 5 -> unlikePost();
                    case 6 -> viewLikeCount();
                    case 7 -> addComment();
                    case 8 -> viewComments();
                    case 9 -> deleteMyComment();
                    case 10 -> editPost();
                    case 11 -> deletePost();
                    case 12 -> back = true;
                    default -> System.out.println("Invalid choice");
                }
            }
        }
    }

    private void createPost() {

        System.out.print("Enter post content: ");
        String content = scanner.nextLine();

        System.out.print("Enter hashtags: ");
        String hashtags = scanner.nextLine();

        boolean created = postService.createPost(content, hashtags);
        System.out.println(created ? "Post created successfully" : "Failed to create post");
    }

    private void createPromotionalPost() {

        System.out.print("Enter content: ");
        String content = scanner.nextLine();

        System.out.print("Enter hashtags: ");
        String hashtags = scanner.nextLine();

        System.out.print("CTA Label (optional): ");
        String ctaLabel = scanner.nextLine();

        System.out.print("CTA Link (optional): ");
        String ctaLink = scanner.nextLine();

        System.out.print("Schedule post? (y/n): ");
        String schedule = scanner.nextLine();

        LocalDateTime scheduledAt = null;
        if (schedule.equalsIgnoreCase("y")) {
            System.out.print("Enter date-time (yyyy-MM-dd HH:mm): ");
            String dt = scanner.nextLine();
            scheduledAt = LocalDateTime.parse(dt, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        }

        System.out.print("Pin this post? (y/n): ");
        boolean pin = scanner.nextLine().equalsIgnoreCase("y");

        boolean created = postService.createPromotionalPost(
                content, hashtags, ctaLabel, ctaLink, scheduledAt, pin
        );

        System.out.println(created ? "Promotional post created" : "Failed to create post");
    }

    private void pinPost() {

        System.out.print("Enter Post ID: ");
        int postId = scanner.nextInt();
        scanner.nextLine();

        boolean updated = postService.togglePin(postId);
        System.out.println(updated ? "Pin status updated" : "Failed to update pin");
    }

    private void sharePost() {

        System.out.print("Enter Post ID to share: ");
        int postId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Add a comment (optional): ");
        String comment = scanner.nextLine();

        boolean shared = postService.sharePost(postId, comment);
        System.out.println(shared ? "Post shared successfully" : "Failed to share post");
    }

    private void viewMyPosts() {

        List<Post> posts = postService.viewMyPosts();

        if (posts.isEmpty()) {
            System.out.println("No posts found");
            return;
        }

        System.out.println("\n--- My Posts ---");
        for (Post post : posts) {
            System.out.println("Post ID: " + post.getPostId());
            System.out.println("Type: " + post.getPostType());
            System.out.println(post.isPinned() ? "[PINNED]" : "");
            System.out.println("Content: " + post.getContent());
            System.out.println("Hashtags: " + post.getHashtags());
            System.out.println("Likes: " + likeService.getLikeCount(post.getPostId()));
            System.out.println("-------------------------");
        }
    }

    private void likePost() {

        System.out.print("Enter Post ID: ");
        int postId = scanner.nextInt();
        scanner.nextLine();

        boolean liked = likeService.likePost(postId);
        System.out.println(liked ? "Post liked" : "Unable to like post");
    }

    private void unlikePost() {

        System.out.print("Enter Post ID: ");
        int postId = scanner.nextInt();
        scanner.nextLine();

        boolean unliked = likeService.unlikePost(postId);
        System.out.println(unliked ? "Post unliked" : "Unable to unlike post");
    }

    private void viewLikeCount() {

        System.out.print("Enter Post ID: ");
        int postId = scanner.nextInt();
        scanner.nextLine();

        int count = likeService.getLikeCount(postId);
        System.out.println("Total Likes: " + count);
    }

    private void addComment() {

        System.out.print("Enter Post ID: ");
        int postId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter comment: ");
        String content = scanner.nextLine();

        boolean added = commentService.addComment(postId, content);
        System.out.println(added ? "Comment added" : "Failed to add comment");
    }

    private void viewComments() {

        System.out.print("Enter Post ID: ");
        int postId = scanner.nextInt();
        scanner.nextLine();

        List<Comment> comments = commentService.viewComments(postId);

        if (comments.isEmpty()) {
            System.out.println("No comments found");
            return;
        }

        for (Comment c : comments) {
            System.out.println("Comment ID: " + c.getCommentId());
            System.out.println("User ID: " + c.getAuthorId());
            System.out.println("Comment: " + c.getContent());
            System.out.println("-------------------------");
        }
    }

    private void deleteMyComment() {

        System.out.print("Enter Comment ID: ");
        int commentId = scanner.nextInt();
        scanner.nextLine();

        boolean deleted = commentService.deleteMyComment(commentId);
        System.out.println(deleted ? "Comment deleted" : "Failed to delete comment");
    }

    private void editPost() {

        System.out.print("Enter Post ID: ");
        int postId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter new content: ");
        String content = scanner.nextLine();

        System.out.print("Enter new hashtags: ");
        String hashtags = scanner.nextLine();

        boolean updated = postService.updateMyPost(postId, content, hashtags);
        System.out.println(updated ? "Post updated" : "Failed to update post");
    }

    private void deletePost() {

        System.out.print("Enter Post ID: ");
        int postId = scanner.nextInt();
        scanner.nextLine();

        boolean deleted = postService.deleteMyPost(postId);
        System.out.println(deleted ? "Post deleted" : "Failed to delete post");
    }
}
