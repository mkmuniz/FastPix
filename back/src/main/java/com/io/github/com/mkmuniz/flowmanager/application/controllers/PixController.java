package com.io.github.com.mkmuniz.flowmanager.application.controllers;

import com.io.github.com.mkmuniz.flowmanager.core.domain.Pix;
import com.io.github.com.mkmuniz.flowmanager.core.ports.in.PixServiceException;
import com.io.github.com.mkmuniz.flowmanager.core.ports.in.PixServicePort;
import com.io.github.com.mkmuniz.flowmanager.core.ports.in.PixServicePort.CreatePixCommand;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pix")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PixController {
    
    private final PixServicePort pixService;

    @PostMapping
    public ResponseEntity<Pix> createPix(@RequestBody CreatePixRequest request) {
        CreatePixCommand command = new CreatePixCommand(
            request.pixKey(),
            request.value(),
            request.description(),
            request.state(),
            request.city(),
            request.userId()
        );
        
        Pix pix = pixService.createPix(command);
        return ResponseEntity.ok(pix);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pix> getPixById(@PathVariable Long id) {
        return pixService.findPixById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Pix>> getAllPix() {
        return ResponseEntity.ok(pixService.findAllPix());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Pix>> getPixByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(pixService.findPixByUserId(userId));
    }

    @PostMapping("/{id}/confirm")
    public ResponseEntity<Void> confirmPayment(@PathVariable Long id) {
        pixService.confirmPayment(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelPix(@PathVariable Long id) {
        pixService.cancelPix(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/qrcode")
    public ResponseEntity<Void> generateQrCode(@PathVariable Long id) {
        pixService.generateQrCode(id);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(PixServiceException.PixNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePixNotFound(PixServiceException.PixNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(PixServiceException.InvalidPixStatusException.class)
    public ResponseEntity<ErrorResponse> handleInvalidStatus(PixServiceException.InvalidPixStatusException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse(ex.getMessage()));
    }
}

record ErrorResponse(
    String message
) {}
