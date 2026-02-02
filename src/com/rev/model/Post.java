package com.rev.model;


import java.time.LocalDateTime;

public class Post {

    private int postId;
    private int authorId;              
    private String content;
    private String hashtags;
    private PostType postType;
    private Integer originalPostId;   
    private LocalDateTime createdAt;
    private LocalDateTime scheduledAt; 
    private boolean isPinned;

    public Post() {}

    public Post(int postId, int authorId, String content,
                String hashtags, PostType postType,
                Integer originalPostId, LocalDateTime createdAt,
                LocalDateTime scheduledAt, boolean isPinned) {

        this.postId = postId;
        this.authorId = authorId;
        this.content = content;
        this.hashtags = hashtags;
        this.postType = postType;
        this.originalPostId = originalPostId;
        this.createdAt = createdAt;
        this.scheduledAt = scheduledAt;
        this.isPinned = isPinned;
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

    public String getHashtags() {
        return hashtags;
    }

    public void setHashtags(String hashtags) {
        this.hashtags = hashtags;
    }

    public PostType getPostType() {
        return postType;
    }

    public void setPostType(PostType postType) {
        this.postType = postType;
    }

    public Integer getOriginalPostId() {
        return originalPostId;
    }

    public void setOriginalPostId(Integer originalPostId) {
        this.originalPostId = originalPostId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(LocalDateTime scheduledAt) {
        this.scheduledAt = scheduledAt;
    }

    public boolean isPinned() {
        return isPinned;
    }

    public void setPinned(boolean isPinned) {
        this.isPinned = isPinned;
    }
}

