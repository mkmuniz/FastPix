package com.io.github.com.mkmuniz.flowmanager.core.service;

import com.io.github.com.mkmuniz.flowmanager.core.domain.Pix;
import com.io.github.com.mkmuniz.flowmanager.core.domain.PixStatus;
import com.io.github.com.mkmuniz.flowmanager.core.domain.QrCode;
import com.io.github.com.mkmuniz.flowmanager.core.ports.in.PixServiceException;
import com.io.github.com.mkmuniz.flowmanager.core.ports.in.PixServicePort;
import com.io.github.com.mkmuniz.flowmanager.core.domain.commands.CreatePixCommand;
import com.io.github.com.mkmuniz.flowmanager.core.ports.out.PixRepositoryPort;
import com.io.github.com.mkmuniz.flowmanager.core.ports.out.QrCodeGeneratorRepositoryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PixService implements PixServicePort {
    
    private final PixRepositoryPort pixRepository;
    private final QrCodeGeneratorRepositoryPort qrCodeGeneratorRepository;

    @Override
    @Transactional
    public Pix createPix(CreatePixCommand command) {
        Pix pix = command.toDomain();
        pix = pixRepository.save(pix);
        
        QrCode qrCode = qrCodeGeneratorRepository.generateQrCode(pix);
        pix.updateQrCode(qrCode.getText(), qrCode.getImage());
        pixRepository.save(pix);
        
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
    @Transactional
    public void confirmPayment(Long pixId) {
        Pix pix = getPix(pixId);
        pix.confirm();
        
        pixRepository.save(pix);
    }

    @Override
    @Transactional
    public void cancelPix(Long pixId) {
        Pix pix = getPix(pixId);
        pix.cancel();
        
        pixRepository.save(pix);
    }

    @Override
    @Transactional
    public void expirePix(Long pixId) {
        Pix pix = getPix(pixId);
        pix.expire();
        
        pixRepository.save(pix);
    }

    private Pix getPix(Long id) {
        return pixRepository.findById(id)
            .orElseThrow(() -> new PixServiceException.PixNotFoundException(id));
    }
}
