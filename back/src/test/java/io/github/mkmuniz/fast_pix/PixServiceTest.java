package io.github.mkmuniz.fast_pix;

import io.github.mkmuniz.fast_pix.core.domain.Pix;
import io.github.mkmuniz.fast_pix.core.domain.QrCode;
import io.github.mkmuniz.fast_pix.core.ports.out.PixRepositoryPort;
import io.github.mkmuniz.fast_pix.core.ports.in.QrCodeServicePort;
import io.github.mkmuniz.fast_pix.core.service.PixService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
    void testfindAllPix_ItShouldReturnAllPix() {
        when(pixRepository.findAll()).thenReturn(pixList);

        List<Pix> result = pixService.getPix();

        assertEquals(pixList, result);
        assertEquals(2, result.size());
        assertNotNull(result);
    }

    @Test
    void testFindAllPix_ItShouldReturnAnEmptyList() {
        when(pixRepository.findAll()).thenReturn(Arrays.asList());

        List<Pix> result = pixService.getPix();

        assertEquals(0, result.size());
    }

    @Test
    void testCreatePix_ItShouldReturnPixCreated() {
        // Arrange
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
}
