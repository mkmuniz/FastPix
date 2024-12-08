package com.io.github.com.mkmuniz.flowmanager.core.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Pix {
    private Long id;
    private String pixKey;
    private BigDecimal value;
    private String description;
    private PixStatus status;
    private String qrCodeText;
    private String qrCodeImage;
    private LocalDateTime createdAt;
    private LocalDateTime confirmedAt;
    private Long userId;
    private String city;
    private String state;

    private Pix() {}

    public Pix(String pixKey, BigDecimal value, String description, String city) {
        this.pixKey = pixKey;
        this.value = value;
        this.description = description;
        this.city = city;
    }

    public void updateQrCode(String qrCodeText, String qrCodeImage) {
        this.qrCodeText = qrCodeText;
        this.qrCodeImage = qrCodeImage;
    }

    public static class Builder {
        private final Pix pix;

        public Builder() {
            pix = new Pix();
            pix.status = PixStatus.PENDING;
            pix.createdAt = LocalDateTime.now();
        }

        public Builder withPixKey(String pixKey) {
            pix.pixKey = pixKey;
            return this;
        }

        public Builder withValue(BigDecimal value) {
            if (value.compareTo(BigDecimal.ZERO) <= 0)
                throw new IllegalArgumentException("Valor do Pix deve ser maior que zero");

            pix.value = value;
            return this;
        }

        public Builder withDescription(String description) {
            pix.description = description;
            return this;
        }

        public Builder withUserId(Long userId) {
            pix.userId = userId;
            return this;
        }

        public Builder withCity(String city) {
            pix.city = city;
            return this;
        }

        public Builder withState(String state) {
            pix.state = state;
            return this;
        }

        public Pix build() {
            validatePix();
            return pix;
        }

        private void validatePix() {
            if (pix.pixKey == null || pix.pixKey.trim().isEmpty())
                throw new IllegalStateException("Chave Pix é obrigatória");

            if (pix.value == null)
                throw new IllegalStateException("Valor é obrigatório");
        }
    }

    public void confirm() {
        if (this.status != PixStatus.PENDING)
            throw new IllegalStateException("Apenas PIX pendentes podem ser confirmados");

        this.status = PixStatus.CONFIRMED;
        this.confirmedAt = LocalDateTime.now();
    }

    public void cancel() {
        if (this.status != PixStatus.PENDING)
            throw new IllegalStateException("Apenas PIX pendentes podem ser cancelados");

        this.status = PixStatus.CANCELLED;
    }

    public void expire() {
        if (this.status != PixStatus.PENDING)
            throw new IllegalStateException("Apenas PIX pendentes podem expirar");

        this.status = PixStatus.EXPIRED;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPixKey() {
        return pixKey;
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public PixStatus getStatus() {
        return status;
    }

    public void setStatus(PixStatus status) {
        this.status = status;
    }

    public String getQrCodeText() {
        return qrCodeText;
    }

    public String getQrCodeImage() {
        return qrCodeImage;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getConfirmedAt() {
        return confirmedAt;
    }

    public void setConfirmedAt(LocalDateTime confirmedAt) {
        this.confirmedAt = confirmedAt;
    }

    public Long getUserId() {
        return userId;
    }

    public String getCity() {
        return city != null ? city : "SAO PAULO";
    }

    public String getState() {
        return state;
    }
}
