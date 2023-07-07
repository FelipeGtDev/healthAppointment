package com.healthAppointment.healthAppointment.model.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactsDTO {
    private String email;
    private List<PhoneDTO> phones;


}
