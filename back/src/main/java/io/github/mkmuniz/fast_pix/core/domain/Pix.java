package io.github.mkmuniz.fast_pix.core.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Pix {
    private Long id;
    private String pixKey;
    private String name;
    private BigDecimal value;
    private PixStatus status;
    private String qrCodeText;
    private String qrCodeImage;
    private String state;
    private String city;
    private LocalDateTime confirmedAt;
    private LocalDateTime createdAt;
    
    private Pix() {}

    public Long getId() {
        return id;
    }

    public String getPixKey() {
        return pixKey;
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public PixStatus getStatus() {
        return status;
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

    public LocalDateTime getConfirmedAt() {
        return confirmedAt;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public static class Builder {
        private final Pix pix;

        public Builder() {
            pix = new Pix();
            pix.status = PixStatus.PENDING;
            pix.createdAt = LocalDateTime.now();
        }

        public Builder withId(Long id) {
            pix.id = id;
            return this;
        }

        public Builder withPixKey(String pixKey) {
            pix.pixKey = pixKey;
            return this;
        }

        public Builder withValue(BigDecimal value) {
            pix.value = value;
            return this;
        }

        public Builder withName(String name) {
            pix.name = name;
            return this;
        }

        public Builder withQrCode(String qrCodeText, String qrCodeImage) {
            pix.qrCodeText = qrCodeText;
            pix.qrCodeImage = qrCodeImage;
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

        public Builder withStatus(PixStatus status) {
            pix.status = status;
            return this;
        }

        public Builder withConfirmedAt(LocalDateTime confirmedAt) {
            pix.confirmedAt = confirmedAt;
            return this;
        }

        public Pix build() {
            validatePix();
            return pix;
        }

        private void validatePix() {
            if (pix.pixKey == null || pix.pixKey.trim().isEmpty()) throw new IllegalStateException("Chave Pix é obrigatória");
            if (pix.value == null || pix.value.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalStateException("Valor do Pix é obrigatório e deve ser maior que zero");
        }
    }
}
