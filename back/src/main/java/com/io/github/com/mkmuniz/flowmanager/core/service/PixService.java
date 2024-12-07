package com.io.github.com.mkmuniz.flowmanager.core.service;

import com.io.github.com.mkmuniz.flowmanager.core.domain.Pix;
import com.io.github.com.mkmuniz.flowmanager.core.domain.PixStatus;
import com.io.github.com.mkmuniz.flowmanager.core.ports.in.PixServiceException;
import com.io.github.com.mkmuniz.flowmanager.core.ports.in.PixServicePort;
import com.io.github.com.mkmuniz.flowmanager.core.ports.out.NotificationPort;
import com.io.github.com.mkmuniz.flowmanager.core.ports.out.PixRepositoryPort;
import com.io.github.com.mkmuniz.flowmanager.core.ports.out.QrCodeGeneratorPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PixService implements PixServicePort {
    
    private final PixRepositoryPort pixRepository;
    private final QrCodeGeneratorPort qrCodeGenerator;
    private final NotificationPort notificationPort;

    @Override
    @Transactional
    public Pix createPix(CreatePixCommand command) {
        Pix pix = command.toDomain();
        pix = pixRepository.save(pix);
        
        qrCodeGenerator.generateQrCode(pix);
        notificationPort.notifyPixCreated(pix);
        
        return pix;
    }

    @Override
    public Optional<Pix> findPixById(Long id) {
        return pixRepository.findById(id);
    }

    @Override
    public List<Pix> findAllPix() {
        return pixRepository.findAll();
    }

    @Override
    public List<Pix> findPixByUserId(Long userId) {
        return pixRepository.findByUserId(userId);
    }

    @Override
    @Transactional
    public void confirmPayment(Long pixId) {
        Pix pix = getPix(pixId);
        pix.confirm();
        
        pixRepository.save(pix);
        notificationPort.notifyPixConfirmed(pix);
    }

    @Override
    @Transactional
    public void cancelPix(Long pixId) {
        Pix pix = getPix(pixId);
        pix.cancel();
        
        pixRepository.save(pix);
        notificationPort.notifyPixCancelled(pix);
    }

    @Override
    @Transactional
    public void expirePix(Long pixId) {
        Pix pix = getPix(pixId);
        pix.expire();
        
        pixRepository.save(pix);
        notificationPort.notifyPixExpired(pix);
    }

    @Override
    @Transactional
    public void generateQrCode(Long pixId) {
        Pix pix = getPix(pixId);
        qrCodeGenerator.generateQrCode(pix);
        pixRepository.save(pix);
    }

    private Pix getPix(Long id) {
        return pixRepository.findById(id)
            .orElseThrow(() -> new PixServiceException.PixNotFoundException(id));
    }
}
