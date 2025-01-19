package io.github.mkmuniz.fast_pix.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import io.github.mkmuniz.fast_pix.application.entity.PixEntity;

public interface PixRepository extends JpaRepository<PixEntity, Long> {}
