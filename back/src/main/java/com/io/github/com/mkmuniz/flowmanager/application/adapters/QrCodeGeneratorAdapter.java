package com.io.github.com.mkmuniz.flowmanager.application.adapters;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Component;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import com.io.github.com.mkmuniz.flowmanager.core.domain.Pix;
import com.io.github.com.mkmuniz.flowmanager.core.ports.out.QrCodeGeneratorPort;

@Component
public class QrCodeGeneratorAdapter implements QrCodeGeneratorPort {
    
    @Override
    public void generateQrCode(Pix pix) {
        try {
            String qrCodeText = generatePixQrCodeText(pix);
            String qrCodeImage = generateQrCodeImage(qrCodeText);
            pix.updateQrCode(qrCodeText, qrCodeImage);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar QR Code", e);
        }
    }
    
    private String generatePixQrCodeText(Pix pix) {
        StringBuilder sb = new StringBuilder();
        
        // Payload Format Indicator
        sb.append("000201");
        
        // Merchant Account Information
        sb.append("26");
        String merchantAccountInfo = String.format(
            "0014BR.GOV.BCB.PIX01%02d%s",
            pix.getPixKey().length(),
            pix.getPixKey()
        );
        sb.append(String.format("%02d%s", merchantAccountInfo.length(), merchantAccountInfo));
        
        // Moeda
        sb.append("5303986");
        
        // Valor da transação
        DecimalFormat df = new DecimalFormat("0.00");
        String amount = df.format(pix.getValue());
        sb.append(String.format("54%02d%s", amount.length(), amount));
        
        // País
        sb.append("5802BR");
        
        // Nome do beneficiário
        String receiverName = pix.getDescription();
        if (receiverName.length() > 25) {
            receiverName = receiverName.substring(0, 25);
        }
        sb.append(String.format("59%02d%s", receiverName.length(), receiverName));
        
        // Cidade
        sb.append("60");
        String city = "SAO PAULO";
        sb.append(String.format("%02d%s", city.length(), city));
        
        // Additional Data Field
        sb.append("6304");
        
        // CRC16
        String crc16 = calculateCRC16(sb.toString());
        sb.append(crc16);
        
        return sb.toString();
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
    
    private String generateQrCodeImage(String content) throws WriterException {
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