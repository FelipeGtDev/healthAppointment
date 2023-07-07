package com.healthAppointment.healthAppointment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HumanName {
    private String prefix;
    private String name;
    private HumanName socialName;
}
