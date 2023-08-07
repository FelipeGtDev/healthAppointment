package com.healthAppointment.healthAppointment.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PratictionerDTO extends PersonDTO {
    private String id;
    @NotNull
    @JsonProperty("regulatory_agency")
    private RegulatoryAgencyDTO regulatoryAgency;
    @NotNull
    @JsonProperty("register_number")
    private String registerNumber;
    @NotNull
    private List<QualificationDTO> qualifications;
    private Boolean active = true;
}
