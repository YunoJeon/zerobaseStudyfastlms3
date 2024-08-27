package com.zerobase.fastlms.admin.model;

import com.zerobase.fastlms.admin.dto.BannerDto;
import lombok.Data;

@Data
public class BannerInput {
    private Long id;
    private String subject;
    private String imageUrl;
    private String link;
    private String openType;
    private int sortOrder;
    private boolean active;
}
