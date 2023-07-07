package com.healthAppointment.healthAppointment.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressDTO {
    private String street;
    private String number;
    private String complement;
    private String district;
    private String city;
    private String state;
    private String zipCode;
}
