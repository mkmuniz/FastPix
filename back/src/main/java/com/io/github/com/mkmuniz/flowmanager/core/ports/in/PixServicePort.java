package com.io.github.com.mkmuniz.flowmanager.core.ports.in;

import com.io.github.com.mkmuniz.flowmanager.core.domain.Pix;
import com.io.github.com.mkmuniz.flowmanager.core.domain.commands.CreatePixCommand;
import java.util.List;
import java.util.Optional;

public interface PixServicePort {
    Pix createPix(CreatePixCommand command);
    Optional<Pix> findPixById(Long id);
    List<Pix> findAllPix();
    void confirmPayment(Long pixId);
    void cancelPix(Long pixId);
    void expirePix(Long pixId);
}
