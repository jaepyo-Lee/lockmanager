package com.ime.lockmanager.major.domain;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity(name = "MAJOR_TABLE")
public class Major {
    @Id
    @GeneratedValue
    private Long id;
    private String representName;

    public String changeRepresentName(String modifiedRepresentName) {
        this.representName = modifiedRepresentName;
        return representName;
    }
}
