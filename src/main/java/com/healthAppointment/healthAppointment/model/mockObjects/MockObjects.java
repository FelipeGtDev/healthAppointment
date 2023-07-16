package com.healthAppointment.healthAppointment.model.mockObjects;

import com.healthAppointment.healthAppointment.model.RegulatoryAgency;
import com.healthAppointment.healthAppointment.model.enums.StateAcronym;

public class MockObjects {


    public RegulatoryAgency getAgency(String id) {
        return new RegulatoryAgency(id, "CREFITO", StateAcronym.RJ, "123456");
    }
}
