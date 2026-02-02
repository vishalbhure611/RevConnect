package com.rev.model;

import java.time.LocalDateTime;


public class Connection {

    private int requesterId;
    private int receiverId;
    private ConnectionStatus status;
    private LocalDateTime createdAt;

    public Connection() {}

    public Connection(int requesterId, int receiverId,
                      ConnectionStatus status, LocalDateTime createdAt) {

        this.requesterId = requesterId;
        this.receiverId = receiverId;
        this.status = status;
        this.createdAt = createdAt;
    }

    public int getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(int requesterId) {
        this.requesterId = requesterId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public ConnectionStatus getStatus() {
        return status;
    }

    public void setStatus(ConnectionStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

