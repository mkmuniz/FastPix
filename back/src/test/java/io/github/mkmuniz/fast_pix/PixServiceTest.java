package io.github.mkmuniz.fast_pix;

import io.github.mkmuniz.fast_pix.core.domain.Pix;
import io.github.mkmuniz.fast_pix.core.service.PixService;
import io.github.mkmuniz.fast_pix.core.ports.out.PixRepositoryPort;
import io.github.mkmuniz.fast_pix.core.exception.PixNotFoundException;

import io.github.mkmuniz.fast_pix.core.domain.QrCode;
import io.github.mkmuniz.fast_pix.core.ports.in.QrCodeServicePort;

import java.util.List;
import java.util.Arrays;
import java.util.Optional;
import java.math.BigDecimal;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class PixServiceTest {
    @Mock
    private PixRepositoryPort pixRepository;

    @Mock
    private QrCodeServicePort qrCodeService;

    @InjectMocks
    private PixService pixService;

    private List<Pix> pixList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Pix firstPixMock = new Pix.Builder()
                .withPixKey("joao.silva@gmail.com")
                .withValue(new BigDecimal("100.00"))
                .withName("João Silva")
                .withCity("São Paulo")
                .withState("SP")
                .build();

        Pix secondPixMock = new Pix.Builder()
                .withPixKey("maria.santos@gmail.com")
                .withValue(new BigDecimal("200.00"))
                .withName("Maria Santos")
                .withCity("Rio de Janeiro")
                .withState("RJ")
                .build();

        pixList = Arrays.asList(firstPixMock, secondPixMock);
    }

    @Test
    @DisplayName("It should return pix created")
    void testCreatePix() {
        Pix inputPix = new Pix.Builder()
            .withPixKey("39898923495")
            .withValue(new BigDecimal("200.00"))
            .withName("Lucas Silva")
            .withCity("Rio de Janeiro")
            .withState("RJ")
            .build();

        QrCode qrCodeMocked = new QrCode.Builder()
                .withText(inputPix.getPixKey())
                .withImage("QRCODE IMAGE")
                .build();

        Pix expectedPix = new Pix.Builder()
                .withPixKey("39898923495")
                .withValue(new BigDecimal("200.00"))
                .withName("Lucas Silva")
                .withCity("Rio de Janeiro")
                .withState("RJ")
                .withQrCode(qrCodeMocked.getText(), qrCodeMocked.getImage())
                .build();

        when(qrCodeService.generateQrCode(any(Pix.class))).thenReturn(qrCodeMocked);
        when(pixRepository.createPix(any(Pix.class))).thenReturn(expectedPix);

        Pix result = pixService.createPix(inputPix);

        assertNotNull(result);
        assertEquals(expectedPix, result);
    }

    @Test
    @DisplayName("It should return error for pix without key")
    void testCreatePix_ErrorPixKey() {
        try {
            new Pix.Builder()
                    .withValue(new BigDecimal("100.00"))
                    .withName("Gabriel Meideiros")
                    .withCity("São Paulo")
                    .withState("SP")
                    .build();
        } catch (Exception e) {
            assertEquals("Chave Pix é obrigatória", e.getMessage());
        }
    }

    @Test
    @DisplayName("It should return error for pix without a value")
    void testCreatePix_ErrorPixValue() {
        try {
            new Pix.Builder()
                    .withPixKey("carlos.gabriel@gmail.com")
                    .withName("Carlos Gabriel")
                    .withCity("São Paulo")
                    .withState("SP")
                    .build();
        } catch (Exception e) {
            assertEquals("Valor do Pix é obrigatório e deve ser maior que zero", e.getMessage());
        }
    }

    @Test
    @DisplayName("It should return pix not found")
    void testFindPixById_NotFound() {
        when(pixRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        assertThrows(PixNotFoundException.class, () -> pixService.getPixById(1L));
    }

    @Test
    @DisplayName("It should return pix found")
    void testFindPixById_Found() {
        when(pixRepository.findById(any(Long.class))).thenReturn(Optional.of(pixList.get(0)));

        Optional<Pix> result = pixService.getPixById(1L);
        assertTrue(result.isPresent());
    }
}
