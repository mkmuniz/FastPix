package com.io.github.com.mkmuniz.flowmanager.core.ports.in;

import com.io.github.com.mkmuniz.flowmanager.core.domain.QrCode;

public interface QrCodeServicePort {
    QrCode generateQrCode(Long pixId);
} 