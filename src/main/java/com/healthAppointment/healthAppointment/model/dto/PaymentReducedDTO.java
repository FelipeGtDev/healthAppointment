package com.healthAppointment.healthAppointment.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentReducedDTO {
    private String id;
    private Boolean paymentSucceeded;
}
