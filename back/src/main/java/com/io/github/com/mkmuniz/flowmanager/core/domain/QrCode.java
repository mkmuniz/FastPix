package com.io.github.com.mkmuniz.flowmanager.core.domain;

public class QrCode {
    private final String text;
    private final String image;

    public QrCode(String text, String image) {
        this.text = text;
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public String getImage() {
        return image;
    }
}
