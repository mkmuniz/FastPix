package io.github.mkmuniz.fast_pix.application.controller;

import io.github.mkmuniz.fast_pix.core.domain.Pix;
import io.github.mkmuniz.fast_pix.core.domain.PixStatus;
import io.github.mkmuniz.fast_pix.core.service.PixService;
import io.github.mkmuniz.fast_pix.application.mapper.PixMapper;
import io.github.mkmuniz.fast_pix.application.entity.PixEntity;
import io.github.mkmuniz.fast_pix.application.dto.PixDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/pix")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class PixController {

    private final PixService pixService;
    private final PixMapper pixMapper;

    @PostMapping
    public ResponseEntity<Pix> createPix(@RequestBody PixDTO request) {
        Pix pix = pixService.createPix(pixMapper.toDomain(request));
        return ResponseEntity.ok(pix);
    }

    @GetMapping
    public ResponseEntity<List<Pix>> getPix() {
        List<Pix> pix = pixService.getPix();
        return ResponseEntity.ok(pix);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Pix>> getPixById(@PathVariable Long id) {
        Optional<Pix> pix = pixService.getPixById(id);
        return ResponseEntity.ok(pix);
    }
}
