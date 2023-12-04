package com.ime.lockmanager.locker.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Embeddable
public class ImageInfo {

    private String imageUrl;
    private String imageName;
}
