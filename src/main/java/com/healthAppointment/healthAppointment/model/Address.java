package com.healthAppointment.healthAppointment.model;


import com.healthAppointment.healthAppointment.model.enums.StateAcronym;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {


    private String street;
    private String number;
    private String complement;
    private String district;
    private String city;
    private StateAcronym state;
    private String zipCode;

}
