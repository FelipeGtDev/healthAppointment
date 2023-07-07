package com.healthAppointment.healthAppointment.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContactsDTO {
    private String email;
    private List<PhoneDTO> phones;


}
