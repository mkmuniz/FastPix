package com.io.github.com.mkmuniz.flowmanager.application.controllers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.io.github.com.mkmuniz.flowmanager.core.domain.Pix;
import com.io.github.com.mkmuniz.flowmanager.core.domain.PixStatus;

public record PixResponse(
    Long id,
    String pixKey,
    BigDecimal value,
    String description,
    PixStatus status,
    String qrCodeText,
    String qrCodeImage,
    LocalDateTime createdAt,
    LocalDateTime confirmedAt,
    String state,
    String city
) {
    public PixResponse(Pix pix) {
        this(
            pix.getId(),
            pix.getPixKey(),
            pix.getValue(),
            pix.getDescription(),
            pix.getStatus(),
            pix.getQrCodeText(),
            pix.getQrCodeImage(),
            pix.getCreatedAt(),
            pix.getConfirmedAt(),
            pix.getState(),
            pix.getCity()
        );
    }
} 