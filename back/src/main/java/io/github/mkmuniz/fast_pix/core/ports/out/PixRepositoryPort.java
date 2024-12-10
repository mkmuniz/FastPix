package io.github.mkmuniz.fast_pix.core.ports.out;

import io.github.mkmuniz.fast_pix.core.domain.Pix;

import java.util.Optional;
import java.util.List;

public interface PixRepositoryPort {
    Pix createPix(Pix pix);

    Optional<Pix> findById(Long id);

    List<Pix> findAll();
}
