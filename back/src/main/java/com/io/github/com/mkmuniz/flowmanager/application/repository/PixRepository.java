package com.io.github.com.mkmuniz.flowmanager.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.io.github.com.mkmuniz.flowmanager.application.entities.PixEntity;
import com.io.github.com.mkmuniz.flowmanager.core.domain.PixStatus;

import java.util.List;

@Repository
public interface PixRepository extends JpaRepository<PixEntity, Long> {
    List<PixEntity> findByUserId(Long userId);
    List<PixEntity> findByStatus(PixStatus status);
}
