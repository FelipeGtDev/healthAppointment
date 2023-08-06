package com.healthAppointment.healthAppointment.model.dto;

import com.healthAppointment.healthAppointment.model.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    private String id;
    private PaymentMethod paymentMethod;
    private Boolean paymentSucceeded;
    private Date paymentDate;
    private PatientDTO patient;
    private AppointmentDTO appointment;
}
