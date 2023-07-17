package com.healthAppointment.healthAppointment.service;

import com.healthAppointment.healthAppointment.model.Qualification;
import com.healthAppointment.healthAppointment.model.dto.QualificationDTO;
import com.healthAppointment.healthAppointment.model.dto.QualificationReducedDTO;

import java.util.List;

public interface IQualificationService {
    QualificationDTO save(QualificationDTO request);

    Qualification addType(String specialityId, String typeId);

    List<Qualification> findByCodeList(List<String> codes);

    Qualification findByCode(String code);

    List<QualificationReducedDTO> listByType(String typeCode);

    List<String> getCodeListFromTypes(List<QualificationDTO> qualifications);

}
