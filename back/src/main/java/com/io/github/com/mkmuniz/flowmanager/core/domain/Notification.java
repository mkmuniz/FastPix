package com.io.github.com.mkmuniz.flowmanager.core.domain;

import java.time.LocalDateTime;

public class Notification {
    private Integer id;
    private Pix pix;
    private String status;
    private LocalDateTime receivedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Pix getPix() {
        return pix;
    }

    public void setPix(Pix pix) {
        this.pix = pix;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDateReceived() {
        return receivedAt;
    }

    public void setDateReceived(LocalDateTime receivedAt) {
        this.receivedAt = receivedAt;
    }
}

