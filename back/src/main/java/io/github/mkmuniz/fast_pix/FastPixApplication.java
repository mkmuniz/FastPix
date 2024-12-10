package io.github.mkmuniz.fast_pix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.github.mkmuniz.fast_pix.application.mapper.PixMapper;
import io.github.mkmuniz.fast_pix.application.adapters.PixRepositoryAdapter;
import io.github.mkmuniz.fast_pix.application.repository.PixRepository;
import io.github.mkmuniz.fast_pix.core.service.PixService;
import io.github.mkmuniz.fast_pix.core.ports.out.PixRepositoryPort;
import io.github.mkmuniz.fast_pix.core.ports.in.PixServicePort;
import io.github.mkmuniz.fast_pix.core.ports.in.QrCodeServicePort;
import io.github.mkmuniz.fast_pix.core.service.QrCodeService;

@SpringBootApplication
public class FastPixApplication {

	public static void main(String[] args) {
		SpringApplication.run(FastPixApplication.class, args);
	}

	@Bean
    public QrCodeServicePort qrCodeService() {
        return new QrCodeService();
    }

	@Bean
    public PixService pixService(PixRepositoryPort pixRepository, QrCodeServicePort qrCodeService) {
        return new PixService(pixRepository, qrCodeService);
    }

	@Bean
    public PixMapper pixMapper() {
        return new PixMapper();
    }
}
