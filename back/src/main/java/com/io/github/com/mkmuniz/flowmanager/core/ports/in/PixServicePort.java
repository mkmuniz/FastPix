package com.io.github.com.mkmuniz.flowmanager.core.ports.in;

import com.io.github.com.mkmuniz.flowmanager.core.domain.Pix;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface PixServicePort {
    Pix createPix(CreatePixCommand command);

    Optional<Pix> findPixById(Long id);

    List<Pix> findAllPix();

    List<Pix> findPixByUserId(Long userId);

    void confirmPayment(Long pixId);

    void cancelPix(Long pixId);

    void expirePix(Long pixId);

    void generateQrCode(Long pixId);

    record CreatePixCommand(
        String pixKey,
        BigDecimal value,
        String description,
        String state,
        String city,
        Long userId
    ) {
        public CreatePixCommand {
            if (pixKey == null || pixKey.trim().isEmpty()) {
                throw new IllegalArgumentException("Chave Pix não pode ser vazia");
            }
            if (value == null || value.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Valor deve ser maior que zero");
            }
            if (userId == null) {
                throw new IllegalArgumentException("ID do usuário é obrigatório");
            }
        }

        public Pix toDomain() {
            return new Pix.Builder()
                .withPixKey(pixKey)
                .withValue(value)
                .withDescription(description)
                .withState(state)
                .withCity(city)
                .withUserId(userId)
                .build();
        }
    }
}
