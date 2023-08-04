package com.healthAppointment.healthAppointment.model;

import com.healthAppointment.healthAppointment.model.e.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "payment")
public class Payment {
    private String id;
    private PaymentMethod paymentMethod;
    private Boolean paymentSucceeded;
    private Date paymentDate;
    private Patient patient;
    private Appointment appointment;
}
