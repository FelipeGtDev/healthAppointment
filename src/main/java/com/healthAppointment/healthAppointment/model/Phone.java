package com.healthAppointment.healthAppointment.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Phone {

    private String areaCode;
    private String number;
    private Boolean isWhatsapp;

}
