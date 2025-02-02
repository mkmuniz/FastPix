package io.github.mkmuniz.fast_pix.application.adapters;

import org.springframework.stereotype.Repository;

import io.github.mkmuniz.fast_pix.application.mapper.PixMapper;
import io.github.mkmuniz.fast_pix.application.entity.PixEntity;
import io.github.mkmuniz.fast_pix.application.repository.PixRepository;

import io.github.mkmuniz.fast_pix.core.domain.Pix;
import io.github.mkmuniz.fast_pix.core.ports.out.PixRepositoryPort;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class PixRepositoryAdapter implements PixRepositoryPort {
    private final PixRepository pixRepository;
    private final PixMapper pixMapper;

    @Override
    public Pix createPix(Pix pix) {
        return pixMapper.toDomain(pixRepository.save(pixMapper.toEntity(pix)));
    }

    @Override
    public Optional<Pix> findById(Long id) {
        return pixRepository.findById(id).map(pixMapper::toDomain);
    }
}
