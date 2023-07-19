package com.healthAppointment.healthAppointment.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.healthAppointment.healthAppointment.model.enums.StateAcronym;
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
public class RegulatoryAgencyDTO {
    private String id;
    @NotNull
    private String name;
    @NotNull
    private StateAcronym state;
    @NotNull
    private QualificationReducedDTO qualification;

    public void setName(String name) {
        this.name = name.toUpperCase();
    }

}
