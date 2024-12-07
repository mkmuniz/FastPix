package com.io.github.com.mkmuniz.flowmanager.application.adapters;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Component;

import com.io.github.com.mkmuniz.flowmanager.core.domain.Pix;
import com.io.github.com.mkmuniz.flowmanager.core.ports.out.QrCodeGeneratorPort;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

@Component
public class QrCodeGeneratorAdapter implements QrCodeGeneratorPort {
    
    @Override
    public void generateQrCode(Pix pix) {
        try {
            // Gera o texto do QR code
            String qrCodeText = generatePixQrCodeText(pix);
            
            // Gera a imagem do QR code
            String qrCodeImage = generateQrCodeImage(qrCodeText);
            
            // Atualiza o Pix com os dados do QR code
            pix.updateQrCode(qrCodeText, qrCodeImage);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar QR Code", e);
        }
    }
    
    private String generatePixQrCodeText(Pix pix) {
        // Formato do Pix dinâmico conforme manual do Bacen
        StringBuilder sb = new StringBuilder();
        sb.append("00020126"); // Payload Format Indicator e Merchant Account Information
        sb.append("5303986"); // Moeda BRL (986)
        sb.append(String.format("54%02d%s", pix.getValue().toString().length(), pix.getValue().toString().replace(".", "")));
        sb.append("5802BR"); // País BR
        sb.append(String.format("59%02d%s", pix.getDescription().length(), pix.getDescription()));
        sb.append(String.format("62%02d%s", pix.getPixKey().length(), pix.getPixKey()));
        sb.append("6304"); // CRC16
        
        return sb.toString();
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