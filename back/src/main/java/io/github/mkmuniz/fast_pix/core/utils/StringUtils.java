package io.github.mkmuniz.fast_pix.core.utils;

import java.text.Normalizer;
import java.security.SecureRandom;

public class StringUtils {
    public static String generateTransactionId() {
        StringBuilder txId = new StringBuilder();
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int length = 25;

        SecureRandom random = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(chars.length());
            txId.append(chars.charAt(index));
        }

        return txId.toString();
    }

    public static String removeAccents(String input) {
        return Normalizer
                .normalize(input, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
    }

    public static String calculateCRC16(String qrCode) {
        int polynom = 0x1021;
        int crc = 0xFFFF;

        for (int i = 0; i < qrCode.length(); i++) {
            crc ^= (qrCode.charAt(i) << 8);
            for (int j = 0; j < 8; j++) {
                if ((crc & 0x8000) != 0) {
                    crc = ((crc << 1) ^ polynom) & 0xFFFF;
                } else {
                    crc = (crc << 1) & 0xFFFF;
                }
            }
        }

        return String.format("%04X", crc & 0xFFFF).toUpperCase();
    }
} 