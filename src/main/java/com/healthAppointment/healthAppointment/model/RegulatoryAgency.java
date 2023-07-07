package com.healthAppointment.healthAppointment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "regulatory_agency")
public class RegulatoryAgency {
    private String id;
    private String name;
    private String state;
    private String number;

}