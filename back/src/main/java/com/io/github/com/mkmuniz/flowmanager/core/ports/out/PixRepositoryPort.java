package com.io.github.com.mkmuniz.flowmanager.core.ports.out;

import com.io.github.com.mkmuniz.flowmanager.core.domain.Pix;
import com.io.github.com.mkmuniz.flowmanager.core.domain.PixStatus;
import java.util.List;
import java.util.Optional;

public interface PixRepositoryPort {
    Pix save(Pix pix);
    
    Optional<Pix> findById(Long id);
    
    List<Pix> findAll();
    
    List<Pix> findByUserId(Long userId);
    
    void delete(Long id);
    
    boolean existsById(Long id);
    
    List<Pix> findByStatus(PixStatus status);
}
