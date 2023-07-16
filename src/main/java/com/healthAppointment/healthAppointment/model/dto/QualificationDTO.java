package com.healthAppointment.healthAppointment.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QualificationDTO {
//    private String id;
    private String name;
    private String code;
    private String description;
    private List<QualificationDTO> types;

    public QualificationDTO(String code){
        this.code = code;
    }
}
