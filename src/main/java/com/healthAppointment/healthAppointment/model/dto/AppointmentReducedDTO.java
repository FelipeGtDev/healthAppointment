package com.healthAppointment.healthAppointment.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentReducedDTO {
    private String id;
    private String date;
    private String time;
}
