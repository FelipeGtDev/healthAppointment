package com.healthAppointment.healthAppointment.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDTO {
    private String street;
    private String number;
    private String complement;
    private String district;
    private String city;
    private String state;
    private String zipCode;
}
