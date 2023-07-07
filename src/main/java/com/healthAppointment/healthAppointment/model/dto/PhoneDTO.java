package com.healthAppointment.healthAppointment.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhoneDTO {

    private String areaCode;
    private String number;
    private Boolean isWhatsapp;
}
