package com.io.github.com.mkmuniz.flowmanager.application.adapters;

import org.springframework.stereotype.Component;
import com.io.github.com.mkmuniz.flowmanager.core.domain.Pix;
import com.io.github.com.mkmuniz.flowmanager.core.ports.out.NotificationPort;

@Component
public class NotificationAdapter implements NotificationPort {
    
    @Override
    public void notifyPixCreated(Pix pix) {
        // Implementação simulada
        System.out.println("Notificação: Pix criado - ID: " + pix.getId());
    }
    
    @Override
    public void notifyPixConfirmed(Pix pix) {
        System.out.println("Notificação: Pix confirmado - ID: " + pix.getId());
    }
    
    @Override
    public void notifyPixCancelled(Pix pix) {
        System.out.println("Notificação: Pix cancelado - ID: " + pix.getId());
    }
    
    @Override
    public void notifyPixExpired(Pix pix) {
        System.out.println("Notificação: Pix expirado - ID: " + pix.getId());
    }
} 