package io.github.mkmuniz.fast_pix.application.controller;

import io.github.mkmuniz.fast_pix.core.domain.Pix;
import io.github.mkmuniz.fast_pix.core.domain.PixStatus;
import io.github.mkmuniz.fast_pix.core.service.PixService;
import io.github.mkmuniz.fast_pix.application.mapper.PixMapper;
import io.github.mkmuniz.fast_pix.application.entity.PixEntity;
import io.github.mkmuniz.fast_pix.application.dto.PixDTO;
import io.github.mkmuniz.fast_pix.application.dto.ErrorResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/pix")
@CrossOrigin(origins = "${app.cors.allowed-origins}")
@AllArgsConstructor
public class PixController {
    private final PixService pixService;
    private final PixMapper pixMapper;

    @PostMapping
    public ResponseEntity<?> createPix(@RequestBody PixDTO request) {
        try {
            return ResponseEntity.ok(pixService.createPix(pixMapper.toDomain(request)));
        } catch (IllegalArgumentException | IllegalStateException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Ocorreu um erro inesperado: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPixById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(pixService.getPixById(id));
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Ocorreu um erro inesperado: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
