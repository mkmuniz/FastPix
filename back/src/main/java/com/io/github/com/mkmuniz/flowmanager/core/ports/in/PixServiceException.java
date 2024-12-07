package com.io.github.com.mkmuniz.flowmanager.core.ports.in;

public class PixServiceException extends RuntimeException {
    public PixServiceException(String message) {
        super(message);
    }

    public PixServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public static class PixNotFoundException extends PixServiceException {
        public PixNotFoundException(Long id) {
            super("Pix não encontrado com ID: " + id);
        }
    }

    public static class InvalidPixStatusException extends PixServiceException {
        public InvalidPixStatusException(String message) {
            super(message);
        }
    }

    public static class QrCodeGenerationException extends PixServiceException {
        public QrCodeGenerationException(String message) {
            super(message);
        }
    }
} 