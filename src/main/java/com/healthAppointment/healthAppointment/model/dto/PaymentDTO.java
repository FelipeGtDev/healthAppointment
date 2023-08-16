package com.healthAppointment.healthAppointment.model.dto;

import com.healthAppointment.healthAppointment.model.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    private String id;
    private PaymentMethod paymentMethod;
    private Double amount;
    private Boolean paymentSucceeded;
    private Date paymentDate;
    private List<AppointmentReducedDTO> appointments;
    private Integer balance;
    private Integer appointmentsPayed;

}
