package io.github.mkmuniz.fast_pix.core.ports.in;

import io.github.mkmuniz.fast_pix.core.domain.Pix;
import io.github.mkmuniz.fast_pix.core.exception.PixNotFoundException;

import java.util.List;
import java.util.Optional;

public interface PixServicePort {
    Pix createPix(Pix pix);

    /**
     * @throws PixNotFoundException
     */
    
    Optional<Pix> getPixById(Long id);

    List<Pix> getPix();
}
