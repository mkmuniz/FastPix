package io.github.mkmuniz.fast_pix;

import io.github.mkmuniz.fast_pix.core.domain.Pix;
import io.github.mkmuniz.fast_pix.core.domain.QrCode;
import io.github.mkmuniz.fast_pix.core.service.QrCodeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class QrCodeServiceTest {
    private QrCodeService qrCodeService;
    private Pix pix;

    @BeforeEach
    void setUp() {
        qrCodeService = new QrCodeService();
        pix = new Pix.Builder()
                .withPixKey("teste@email.com")
                .withValue(new BigDecimal("100.00"))
                .withName("João Silva")
                .withCity("São Paulo")
                .withState("SP")
                .build();
    }

    @Test
    @DisplayName("It should generate QR code successfully")
    void testGenerateQrCode() {
        QrCode result = qrCodeService.generateQrCode(pix);

        assertNotNull(result);
        assertNotNull(result.getText());
        assertNotNull(result.getImage());

        assertTrue(result.getText().startsWith("000201"));
        assertTrue(result.getImage().startsWith("data:image/png;base64,"));
    }

    @Test
    @DisplayName("It should handle long name correctly")
    void testGenerateQrCodeWithLongName() {
        Pix pixWithLongName = new Pix.Builder()
                .withPixKey("maria@gmail.com")
                .withValue(new BigDecimal("100.00"))
                .withName("Maria Fernanda Rodrigues Santos Pereira Lima")
                .withCity("São Paulo")
                .withState("SP")
                .build();

        QrCode result = qrCodeService.generateQrCode(pixWithLongName);

        assertNotNull(result);
        String qrCodeText = result.getText();

        String name = qrCodeText.substring(qrCodeText.indexOf("59") + 4, qrCodeText.indexOf("60")).trim();
        assertEquals("MARIA FERNANDA RODRIGUES", name);
        assertTrue(name.length() <= 25);
    }

    @Test
    @DisplayName("It should handle long city name correctly")
    void testGenerateQrCodeWithLongCity() {
        Pix pixWithLongCity = new Pix.Builder()
                .withPixKey("joao.silva@gmail.com")
                .withValue(new BigDecimal("100.00"))
                .withName("João Silva")
                .withCity("São José dos Campos do Rio Grande")
                .withState("SP")
                .build();

        QrCode result = qrCodeService.generateQrCode(pixWithLongCity);

        assertNotNull(result);
        assertTrue(result.getText().contains("SAO JOSE DOS CA"));
    }

    @Test
    @DisplayName("It should handle special characters correctly")
    void testGenerateQrCodeWithSpecialCharacters() {
        Pix pixWithSpecialChars = new Pix.Builder()
                .withPixKey("joao1998@gmail.com")
                .withValue(new BigDecimal("100.00"))
                .withName("João áéíóú")
                .withCity("São Paulo")
                .withState("SP")
                .build();

        QrCode result = qrCodeService.generateQrCode(pixWithSpecialChars);

        assertNotNull(result);
        assertTrue(result.getText().contains("JOAO AEIOU"));
    }

    @Test
    @DisplayName("It should format decimal values correctly")
    void testGenerateQrCodeWithDecimalValues() {
        Pix pixWithDecimal = new Pix.Builder()
                .withPixKey("joao_slv@gmai;l.com")
                .withValue(new BigDecimal("99.99"))
                .withName("João Silva")
                .withCity("São Paulo")
                .withState("SP")
                .build();

        QrCode result = qrCodeService.generateQrCode(pixWithDecimal);

        assertNotNull(result);
        assertTrue(result.getText().contains("99.99"));
    }
}
