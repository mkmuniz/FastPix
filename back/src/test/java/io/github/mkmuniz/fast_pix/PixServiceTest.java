package io.github.mkmuniz.fast_pix;

import io.github.mkmuniz.fast_pix.core.domain.Pix;
import io.github.mkmuniz.fast_pix.core.domain.QrCode;
import io.github.mkmuniz.fast_pix.core.ports.out.PixRepositoryPort;
import io.github.mkmuniz.fast_pix.core.ports.in.QrCodeServicePort;
import io.github.mkmuniz.fast_pix.core.service.PixService;
import io.github.mkmuniz.fast_pix.core.exception.PixNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.DisplayName;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.InjectMocks;

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
        
        Pix firstPix = new Pix.Builder()
            .withPixKey("teste1@email.com")
            .withValue(new BigDecimal("100.00"))
            .withName("João Silva")
            .withCity("São Paulo")
            .withState("SP")
            .build();

        Pix secondPix = new Pix.Builder()
            .withPixKey("teste2@email.com")
            .withValue(new BigDecimal("200.00"))
            .withName("Maria Santos")
            .withCity("Rio de Janeiro")
            .withState("RJ")
            .build();

        pixList = Arrays.asList(firstPix, secondPix);
    }

    @Test
    @DisplayName("It should return pix created")
    void testCreatePix() {
        Pix inputPix = new Pix.Builder()
            .withPixKey("teste1@email.com")
            .withValue(new BigDecimal("100.00"))
            .withName("João Silva")
            .withCity("São Paulo")
            .withState("SP")
            .build();

        QrCode qrCodeMocked = new QrCode.Builder()
            .withText("teste1@gmail.com")
            .withImage("QRCODE IMAGE")
            .build();

        Pix expectedPix = new Pix.Builder()
            .withPixKey("teste1@email.com")
            .withValue(new BigDecimal("100.00"))
            .withName("João Silva")
            .withCity("São Paulo")
            .withState("SP")
            .withQrCode(qrCodeMocked.getText(), qrCodeMocked.getImage())
            .build();

        when(qrCodeService.generateQrCode(any(Pix.class))).thenReturn(qrCodeMocked);
        when(pixRepository.createPix(any(Pix.class))).thenReturn(expectedPix);

        Pix result = pixService.createPix(inputPix);

        assertNotNull(result);
        assertEquals(expectedPix.getPixKey(), result.getPixKey());
        assertEquals(expectedPix.getValue(), result.getValue());
        assertEquals(expectedPix.getName(), result.getName());
        assertEquals(expectedPix.getCity(), result.getCity());
        assertEquals(expectedPix.getState(), result.getState());
        assertEquals(expectedPix.getQrCodeText(), result.getQrCodeText());
        assertEquals(expectedPix.getQrCodeImage(), result.getQrCodeImage());
    }

    @Test
    @DisplayName("It should return error for pix without key")
    void testCreatePix_ErrorPixKey() {
        try {
            Pix inputPix = new Pix.Builder()
                .withValue(new BigDecimal("100.00"))
                .withName("João Silva")
                .withCity("São Paulo")
                .withState("SP")
                .build();
            
        } catch (Exception e) {
            assertEquals("Chave Pix é obrigatória", e.getMessage());
        };
    }

    @Test
    @DisplayName("It should return error for pix without a value")
    void testCreatePix_ErrorPixValue() {
        try {
            Pix inputPix = new Pix.Builder()
                .withPixKey("teste1@email.com")
                .withName("João Silva")
                .withCity("São Paulo")
                .withState("SP")
                .build();
            
        } catch (Exception e) {
            assertEquals("Valor do Pix é obrigatório e deve ser maior que zero", e.getMessage());
        };
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
