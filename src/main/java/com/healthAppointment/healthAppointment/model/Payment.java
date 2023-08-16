package com.healthAppointment.healthAppointment.model;

import com.healthAppointment.healthAppointment.model.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "payment")
public class Payment {
    private String id;
    private PaymentMethod paymentMethod;
    private Double amount;
    private Boolean paymentSucceeded;
    private Date paymentDate;
    private List<Appointment> appointments;
    private Integer balance;
    private Integer appointmentsPayed;
}
