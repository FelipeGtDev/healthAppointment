package com.healthAppointment.healthAppointment.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PhoneDTO {
    @NotNull
    @JsonProperty("area_code")
    private String areaCode;
    @NotNull
    private String number;
    @NotNull
    @JsonProperty("is_whatsapp")
    private Boolean isWhatsapp;
}
