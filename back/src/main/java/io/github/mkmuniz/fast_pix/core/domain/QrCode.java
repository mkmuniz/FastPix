package io.github.mkmuniz.fast_pix.core.domain;

public class QrCode {
    private String text;
    private String image;

    private QrCode() {}

    public String getText() {
        return text;
    }

    public String getImage() {
        return image;
    }

    public static class Builder {
        private final QrCode qrCode;

        public Builder() {
            qrCode = new QrCode();
        }

        public Builder withText(String text) {
            qrCode.text = text;

            return this;
        }

        public Builder withImage(String image) {
            qrCode.image = image;
            
            return this;
        }

        public QrCode build() {
            return qrCode;
        }
    }
}
