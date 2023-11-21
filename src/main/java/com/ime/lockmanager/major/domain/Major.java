package com.ime.lockmanager.major.domain;

import javax.persistence.*;

@Entity(name = "MAJOR_TABLE")
public class Major {
    @Id
    @GeneratedValue
    private Long id;
    private String representName;
}
