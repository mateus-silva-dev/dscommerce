package com.devsuperior.dscommerce.dto;

import com.devsuperior.dscommerce.entities.Payment;

import java.time.Instant;

public record PaymentDTO(
        Long id,
        Instant moment
) {
    public PaymentDTO(Payment payment) {
        this(payment.getId(), payment.getMoment());
    }
}
