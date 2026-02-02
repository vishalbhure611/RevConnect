package com.rev.model;



import java.time.LocalDateTime;

public class Comment {

    private int commentId;
    private int postId;       
    private int authorId;     
    private String content;
    private LocalDateTime createdAt;

    public Comment() {}

    public Comment(int commentId, int postId, int authorId,
                   String content, LocalDateTime createdAt) {

        this.commentId = commentId;
        this.postId = postId;
        this.authorId = authorId;
        this.content = content;
        this.createdAt = createdAt;
    }


    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
