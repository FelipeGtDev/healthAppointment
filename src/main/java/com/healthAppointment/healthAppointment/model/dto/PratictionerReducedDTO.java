package com.healthAppointment.healthAppointment.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class PratictionerReducedDTO {
    @NotNull
    private String id;
    @NotNull
    private HumanNameDTO name;
    @NotNull
    private List<QualificationReducedDTO> qualifications;

    public PratictionerReducedDTO(String pratictionerId) {
        this.id = pratictionerId;
    }
}
