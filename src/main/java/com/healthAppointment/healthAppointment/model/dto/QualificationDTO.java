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
public class QualificationDTO {
    @NotNull
    private String name;
    @NotNull
    private String code;
    private String description;
    @NotNull
    private List<QualificationDTO> types;

    public QualificationDTO(String code){
        this.code = code;
    }
}
