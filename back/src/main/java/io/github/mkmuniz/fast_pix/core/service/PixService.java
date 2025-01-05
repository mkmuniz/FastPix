package io.github.mkmuniz.fast_pix.core.service;

import io.github.mkmuniz.fast_pix.core.ports.in.PixServicePort;
import io.github.mkmuniz.fast_pix.core.ports.out.PixRepositoryPort;
import io.github.mkmuniz.fast_pix.core.domain.Pix;
import io.github.mkmuniz.fast_pix.core.domain.QrCode;
import io.github.mkmuniz.fast_pix.core.ports.in.QrCodeServicePort;
import io.github.mkmuniz.fast_pix.core.exception.PixNotFoundException;

import java.util.List;
import java.util.Optional;

public class PixService implements PixServicePort {
    
    private final PixRepositoryPort pixRepository;
    private final QrCodeServicePort qrCodeService;

    public PixService(PixRepositoryPort pixRepository, QrCodeServicePort qrCodeService) {
        this.pixRepository = pixRepository;
        this.qrCodeService = qrCodeService;
    }

    @Override
    public Pix createPix(Pix pix) {
        QrCode qrCode = qrCodeService.generateQrCode(pix);
            
        pix = new Pix.Builder()
            .withPixKey(pix.getPixKey())
            .withValue(pix.getValue())
            .withName(pix.getName())
            .withCity(pix.getCity())
            .withState(pix.getState())
            .withQrCode(qrCode.getText(), qrCode.getImage())
            .build();

        return pixRepository.createPix(pix);
    }

    @Override
    public Optional<Pix> getPixById(Long id) {
        Optional<Pix> pix = pixRepository.findById(id);
        
        if (pix.isEmpty())
            throw new PixNotFoundException("Pix not found with id: " + id);

        return pix;
    }

    @Override
    public List<Pix> getPix() {
        return pixRepository.findAll();
    }
}
