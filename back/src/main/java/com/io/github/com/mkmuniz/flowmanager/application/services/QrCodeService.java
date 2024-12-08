package com.io.github.com.mkmuniz.flowmanager.application.services;

import com.io.github.com.mkmuniz.flowmanager.core.domain.QrCode;
import com.io.github.com.mkmuniz.flowmanager.core.ports.in.QrCodeServicePort;
import com.io.github.com.mkmuniz.flowmanager.core.ports.out.PixRepositoryPort;
import com.io.github.com.mkmuniz.flowmanager.core.domain.Pix;
import com.io.github.com.mkmuniz.flowmanager.core.ports.out.QrCodeGeneratorRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QrCodeService implements QrCodeServicePort {
    private final PixRepositoryPort pixRepository;
    private final QrCodeGeneratorRepositoryPort qrCodeGeneratorRepository;

    @Override
    @Transactional
    public QrCode generateQrCode(Long pixId) {
        Pix pix = pixRepository.findById(pixId)
            .orElseThrow(() -> new RuntimeException("Pix não encontrado"));
            
        QrCode qrCode = qrCodeGeneratorRepository.generateQrCode(pix);
        pix.updateQrCode(qrCode.getText(), qrCode.getImage());
        pixRepository.save(pix);
        
        return qrCode;
    }
} 