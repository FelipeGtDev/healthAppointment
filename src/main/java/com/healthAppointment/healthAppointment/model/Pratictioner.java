package com.healthAppointment.healthAppointment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "pratictioner")
public class Pratictioner extends Person {
    private String id;
    private RegulatoryAgency regulatoryAgency;
    private List<Speciality> specialties;

}
