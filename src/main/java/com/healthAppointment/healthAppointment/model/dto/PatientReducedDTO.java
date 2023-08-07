package com.healthAppointment.healthAppointment.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatientReducedDTO {
    @NotNull
    private String id;

    @NotNull
    private HumanNameDTO name;

    private ContactsDTO contacts;

    public PatientReducedDTO(String id) {
        this.id = id;
    }
}
