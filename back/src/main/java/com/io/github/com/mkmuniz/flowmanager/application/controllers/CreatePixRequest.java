package com.io.github.com.mkmuniz.flowmanager.application.controllers;

import java.math.BigDecimal;

public record CreatePixRequest(
    String pixKey,
    BigDecimal value,
    String description,
    String state,
    String city
) {} 