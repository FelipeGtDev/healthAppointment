package com.healthAppointment.healthAppointment.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.healthAppointment.healthAppointment.model.enums.StateAcronym;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressDTO {
    private String street;
    private String number;
    private String complement;
    private String district;
    private String city;
    private StateAcronym state;
    private String zipCode;
}
