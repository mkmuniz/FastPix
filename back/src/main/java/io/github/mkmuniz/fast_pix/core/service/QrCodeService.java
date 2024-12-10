package io.github.mkmuniz.fast_pix.core.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import io.github.mkmuniz.fast_pix.core.domain.Pix;
import io.github.mkmuniz.fast_pix.core.domain.QrCode;
import io.github.mkmuniz.fast_pix.core.ports.in.QrCodeServicePort;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.util.Base64;
import java.text.Normalizer;

public class QrCodeService implements QrCodeServicePort {

    @Override
    public QrCode generateQrCode(Pix pix) {
        String pixCode = generatePixQrCodeText(pix);
        String qrCodeImage = generateQrCodeImage(pixCode);
        
        return new QrCode.Builder()
            .withText(pixCode)
            .withImage(qrCodeImage)
            .build();
    }

    private String generatePixQrCodeText(Pix pix) {
        StringBuilder sb = new StringBuilder();
        
        sb.append("000201");
        
        String pixKey = validatePixKey(pix.getPixKey());
        String merchantAccountInfo = String.format(
            "0014BR.GOV.BCB.PIX01%02d%s",
            pixKey.length(),
            pixKey
        );
        sb.append(String.format("26%02d%s", merchantAccountInfo.length(), merchantAccountInfo));
        
        sb.append("5303986");
        
        DecimalFormat df = new DecimalFormat("0.00");
        String amount = df.format(pix.getValue());
        sb.append(String.format("54%02d%s", amount.length(), amount));
        
        sb.append("5802BR");

        String receiverName = removeAccents(pix.getName().toUpperCase());
        if (receiverName.length() > 25) {
            receiverName = receiverName.substring(0, 25);
        }
        sb.append(String.format("59%02d%s", receiverName.length(), receiverName));

        String city = removeAccents(pix.getCity().toUpperCase());
        if (city.length() > 15) {
            city = city.substring(0, 15);
        }
        sb.append(String.format("60%02d%s", city.length(), city));
        
        String txId = generateTransactionId();
        sb.append("62")
          .append(String.format("%02d", txId.length() + 4))
          .append("05")
          .append(String.format("%02d", txId.length()))
          .append(txId);
        
        sb.append("6304");
        String crc16 = calculateCRC16(sb.toString());
        sb.append(crc16);
        
        return sb.toString();
    }

    private String validatePixKey(String pixKey) {
        if (pixKey == null || pixKey.isEmpty()) {
            throw new IllegalArgumentException("Chave Pix inválida.");
        }
        if (pixKey.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            return pixKey; // E-mail
        } else if (pixKey.matches("^[0-9]{11}$")) {
            return "CPF:" + pixKey; // CPF
        } else if (pixKey.matches("^[0-9]{14}$")) {
            return "CNPJ:" + pixKey; // CNPJ
        } else if (pixKey.matches("^\\+55[0-9]{10,11}$")) {
            return pixKey; // Telefone
        } else if (pixKey.matches("^[a-zA-Z0-9-]{36}$")) {
            return pixKey; // Chave aleatória
        } else {
            throw new IllegalArgumentException("Formato de chave Pix inválido.");
        }
    }

    private String removeAccents(String input) {
        return Normalizer.normalize(input, Normalizer.Form.NFD)
                         .replaceAll("[^\\p{ASCII}]", "");
    }

    private String generateTransactionId() {
        return "***";
    }

    private String calculateCRC16(String qrCode) {
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

    private String generateQrCodeImage(String content) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 300, 300);
            
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
            
            return "data:image/png;base64," + Base64.getEncoder().encodeToString(outputStream.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar imagem do QR Code", e);
        }
    }
}
