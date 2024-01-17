package com.ime.lockmanager.major.domain;

import com.ime.lockmanager.common.domain.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity(name = "MAJOR_TABLE")
public class Major extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    public static Major of(String name) {
        return Major.builder()
                .name(name)
                .build();
    }

    public String changeName(String modifiedRepresentName) {
        this.name = modifiedRepresentName;
        return name;
    }
}
