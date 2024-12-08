package com.io.github.com.mkmuniz.flowmanager.core.domain.commands;

import com.io.github.com.mkmuniz.flowmanager.core.domain.Pix;
import java.math.BigDecimal;

public record CreatePixCommand(
    String pixKey,
    BigDecimal value,
    String description,
    String state,
    String city
) {
    public CreatePixCommand {
        if (pixKey == null || pixKey.trim().isEmpty()) {
            throw new IllegalArgumentException("Chave Pix não pode ser vazia");
        }
        if (value == null || value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor deve ser maior que zero");
        }
    }

    public Pix toDomain() {
        return new Pix.Builder()
            .withPixKey(pixKey)
            .withValue(value)
            .withDescription(description)
            .withState(state)
            .withCity(city)
            .build();
    }
} 