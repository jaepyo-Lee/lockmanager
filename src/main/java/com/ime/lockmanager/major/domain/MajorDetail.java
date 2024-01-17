package com.ime.lockmanager.major.domain;

import com.ime.lockmanager.major.domain.Major;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity(name = "MAJOR_DETAIL_TABLE")
public class MajorDetail {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "major_id")
    private Major major;
}
