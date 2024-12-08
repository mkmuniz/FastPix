package com.io.github.com.mkmuniz.flowmanager.application.controllers;

import com.io.github.com.mkmuniz.flowmanager.core.domain.Pix;
import com.io.github.com.mkmuniz.flowmanager.core.domain.PixStatus;
import com.io.github.com.mkmuniz.flowmanager.core.domain.QrCode;
import com.io.github.com.mkmuniz.flowmanager.core.domain.commands.CreatePixCommand;
import com.io.github.com.mkmuniz.flowmanager.core.ports.in.PixServiceException;
import com.io.github.com.mkmuniz.flowmanager.core.ports.out.PixRepositoryPort;
import com.io.github.com.mkmuniz.flowmanager.core.ports.out.QrCodeGeneratorRepositoryPort;
import com.io.github.com.mkmuniz.flowmanager.core.service.PixService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PixServiceTest {

    @Mock
    private PixRepositoryPort pixRepository;

    @Mock
    private QrCodeGeneratorRepositoryPort qrCodeGeneratorRepository;

    private Pix pix;

    @BeforeEach
    void setUp() {
        CreatePixCommand command = new CreatePixCommand(
            "test@email.com",
            new BigDecimal("100.00"),
            "Test description",
            "SP",
            "São Paulo"
        );

        pix = command.toDomain();
    }

    @Test
    void createPixTestSuccess() {
        pixRepository.save(pix);

        Optional<Pix> result = pixRepository.findById(pix.getId());

        assertNotNull(result);
    }

    @Test
    void findPixByIdTest() {
        Optional<Pix> result = pixRepository.findById(pix.getId());

        assertNotNull(result);
    }

    @Test
    void findAllTest() {
        pixRepository.save(pix);
        
        List<Pix> result = pixRepository.findAll();

        assertTrue(result.size() > 0);
    }
} 