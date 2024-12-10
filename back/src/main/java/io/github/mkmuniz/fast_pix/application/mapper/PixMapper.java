package io.github.mkmuniz.fast_pix.application.mapper;

import io.github.mkmuniz.fast_pix.core.domain.Pix;
import io.github.mkmuniz.fast_pix.core.domain.PixStatus;
import io.github.mkmuniz.fast_pix.application.dto.PixDTO;
import io.github.mkmuniz.fast_pix.application.entity.PixEntity;

public class PixMapper {

    public Pix toDomain(PixEntity entity) {
        if (entity == null) {
            return null;
        }

        return new Pix.Builder()
                .withId(entity.getId())
                .withPixKey(entity.getPixKey())
                .withValue(entity.getValue())
                .withName(entity.getName())
                .withStatus(entity.getStatus())
                .withQrCode(entity.getQrCodeText(), entity.getQrCodeImage())
                .withCity(entity.getCity())
                .withState(entity.getState())
                .withConfirmedAt(entity.getConfirmedAt())
                .build();
    }

    public Pix toDomain(PixDTO dto) {
        if (dto == null) {
            return null;
        }

        return new Pix.Builder()
                .withPixKey(dto.getPixKey())
                .withValue(dto.getValue())
                .withName(dto.getName())
                .withCity(dto.getCity())
                .withState(dto.getState())
                .build();
    }

    public PixEntity toEntity(Pix domain) {
        if (domain == null) {
            return null;
        }

        PixEntity entity = new PixEntity();
        entity.setId(domain.getId());
        entity.setPixKey(domain.getPixKey());
        entity.setValue(domain.getValue());
        entity.setName(domain.getName());
        entity.setStatus(domain.getStatus());
        entity.setQrCodeText(domain.getQrCodeText());
        entity.setQrCodeImage(domain.getQrCodeImage());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setConfirmedAt(domain.getConfirmedAt());
        entity.setCity(domain.getCity());
        entity.setState(domain.getState());
        return entity;
    }

    public PixDTO toDTO(Pix domain) {
        if (domain == null) {
            return null;
        }

        PixDTO dto = new PixDTO();
        dto.setPixKey(domain.getPixKey());
        dto.setValue(domain.getValue());
        dto.setName(domain.getName());
        dto.setCity(domain.getCity());
        dto.setState(domain.getState());
        return dto;
    }
}
