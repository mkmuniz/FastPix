package com.io.github.com.mkmuniz.flowmanager.application.repository;

import org.springframework.stereotype.Component;

import com.io.github.com.mkmuniz.flowmanager.application.entities.PixEntity;
import com.io.github.com.mkmuniz.flowmanager.application.entities.UserEntity;
import com.io.github.com.mkmuniz.flowmanager.core.domain.Pix;

@Component
public class PixMapper {
    
    public Pix toDomain(PixEntity entity) {
        if (entity == null) return null;
        
        Pix pix = new Pix.Builder()
            .withPixKey(entity.getPixKey())
            .withValue(entity.getValue())
            .withDescription(entity.getDescription())
            .withUserId(entity.getUser() != null ? entity.getUser().getId() : null)
            .build();
        
        pix.setId(entity.getId());
        pix.setStatus(entity.getStatus());
        pix.setCreatedAt(entity.getCreatedAt());
        pix.setConfirmedAt(entity.getConfirmedAt());
        pix.updateQrCode(entity.getQrCodeText(), entity.getQrCodeImage());
        
        return pix;
    }

    public PixEntity toEntity(Pix domain) {
        if (domain == null) return null;
        PixEntity entity = new PixEntity();
        entity.setId(domain.getId());
        entity.setPixKey(domain.getPixKey());
        entity.setValue(domain.getValue());
        entity.setDescription(domain.getDescription());
        entity.setStatus(domain.getStatus());
        entity.setQrCodeText(domain.getQrCodeText());
        entity.setQrCodeImage(domain.getQrCodeImage());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setConfirmedAt(domain.getConfirmedAt());
        
        if (domain.getUserId() != null) {
            UserEntity user = new UserEntity();
            user.setId(domain.getUserId());
            entity.setUser(user);
        }
        
        return entity;
    }
} 