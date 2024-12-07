package com.io.github.com.mkmuniz.flowmanager.core.ports.out;

import com.io.github.com.mkmuniz.flowmanager.core.domain.Pix;

public interface QrCodeGeneratorPort {
    void generateQrCode(Pix pix);
} 