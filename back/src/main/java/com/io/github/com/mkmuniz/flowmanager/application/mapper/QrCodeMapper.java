package com.io.github.com.mkmuniz.flowmanager.application.mapper;

import com.io.github.com.mkmuniz.flowmanager.application.entities.QrCodeEntity;
import com.io.github.com.mkmuniz.flowmanager.core.domain.QrCode;
import org.springframework.stereotype.Component;

@Component
public class QrCodeMapper {
    
    public QrCode toDomain(QrCodeEntity entity) {
        if (entity == null) return null;
        return new QrCode(entity.getText(), entity.getImage());
    }

    public QrCodeEntity toEntity(QrCode domain) {
        if (domain == null) return null;
        QrCodeEntity entity = new QrCodeEntity();
        entity.setText(domain.getText());
        entity.setImage(domain.getImage());
        return entity;
    }
}