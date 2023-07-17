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
public class RegulatoryAgencyDTO {
    private String id;
    private String name;
    private StateAcronym state;
    private QualificationReducedDTO qualification;

    public void setName(String name) {
        this.name = name.toUpperCase();
    }

}
