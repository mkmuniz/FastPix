package com.io.github.com.mkmuniz.flowmanager.application.repository;

import org.springframework.stereotype.Repository;

import com.io.github.com.mkmuniz.flowmanager.application.entities.QrCodeEntity;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface QrCodeGeneratorRepository extends JpaRepository<QrCodeEntity, Long> {
}
