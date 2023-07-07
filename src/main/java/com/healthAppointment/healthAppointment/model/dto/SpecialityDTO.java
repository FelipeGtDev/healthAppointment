package com.healthAppointment.healthAppointment.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Document(collection = "speciality")
public class SpecialityDTO {
    private String id;
    private String name;
    private String code;
    private String description;
    private String type;
}
