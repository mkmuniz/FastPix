package com.io.github.com.mkmuniz.flowmanager.core.ports.out;

import com.io.github.com.mkmuniz.flowmanager.core.domain.Pix;

public interface NotificationPort {
    void notifyPixCreated(Pix pix);
    
    void notifyPixConfirmed(Pix pix);
    
    void notifyPixCancelled(Pix pix);
    
    void notifyPixExpired(Pix pix);
} 