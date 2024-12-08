package com.io.github.com.mkmuniz.flowmanager.core.ports.out;

import com.io.github.com.mkmuniz.flowmanager.core.domain.Pix;
import com.io.github.com.mkmuniz.flowmanager.core.domain.QrCode;

public interface QrCodeGeneratorRepositoryPort {
    QrCode generateQrCode(Pix pix);
} 