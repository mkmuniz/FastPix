package com.io.github.com.mkmuniz.flowmanager.application.repository;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.io.github.com.mkmuniz.flowmanager.core.ports.out.PixRepositoryPort;
import com.io.github.com.mkmuniz.flowmanager.application.entities.PixEntity;
import com.io.github.com.mkmuniz.flowmanager.application.mapper.PixMapper;
import com.io.github.com.mkmuniz.flowmanager.core.domain.PixStatus;
import com.io.github.com.mkmuniz.flowmanager.core.domain.Pix;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PixRepositoryAdapter implements PixRepositoryPort {
    private final PixRepository pixRepository;
    private final PixMapper pixMapper;

    @Override
    public Pix save(Pix pix) {
        PixEntity pixEntity = pixMapper.toEntity(pix);
        pixEntity = pixRepository.save(pixEntity);
        return pixMapper.toDomain(pixEntity);
    }

    @Override
    public Optional<Pix> findById(Long id) {
        return pixRepository.findById(id)
            .map(pixMapper::toDomain);
    }

    @Override
    public List<Pix> findAll() {
        return pixRepository.findAll().stream()
            .map(pixMapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        pixRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return pixRepository.existsById(id);
    }

    @Override
    public List<Pix> findByStatus(PixStatus status) {
        return pixRepository.findByStatus(status).stream()
            .map(pixMapper::toDomain)
            .collect(Collectors.toList());
    }
}
