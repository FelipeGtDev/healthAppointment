package com.healthAppointment.healthAppointment.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QualificationReducedDTO {
    private String name;
    private String code;

    public QualificationReducedDTO(String code) {
        this.code = code;
    }
}
