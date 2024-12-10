package io.github.mkmuniz.fast_pix.core.ports.in;

import io.github.mkmuniz.fast_pix.core.domain.Pix;
import io.github.mkmuniz.fast_pix.core.domain.QrCode;

public interface QrCodeServicePort {
    QrCode generateQrCode(Pix pix);
}
