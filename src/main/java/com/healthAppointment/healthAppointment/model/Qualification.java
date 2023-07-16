package com.healthAppointment.healthAppointment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Document(collection = "qualification")
public class Qualification {
    private String id;
    private String name;
    private String code;
    private String description;
    private List<Qualification> types = null;

    public Qualification(String name, String code, String description, List<String> typeCodes) {
        this.name = name;
        this.code = code;
        this.description = description;
        this.types = new ArrayList<>();
        for (String typeCode : typeCodes){
            this.types.add(setTypeCodes(typeCode));
        }
    }

    public Qualification(String code){
        this.code = code;
    }

    public Qualification setTypeCodes(String code){
        var type = new Qualification();
        type.code = code;
        return type;
    }

    public void addType(Qualification type){
        if(this.types == null){
            this.types = new ArrayList<>();
        }
        for (Qualification t : this.types){
            if (t.getId().equals(type.getId())){
                return;
            }
        }

        if (type.types != null){
            return;
        }
        this.types.add(type);
    }
}
