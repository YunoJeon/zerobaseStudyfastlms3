package com.zerobase.fastlms.admin.dto;

import com.zerobase.fastlms.admin.entity.Banner;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BannerDto {

    private Long id;
    private String subject;
    private String imageUrl;
    private String link;
    private String openType;
    private int sortOrder;
    private boolean active;
    private String regDt;

    public static BannerDto of(Banner banner) {
        return BannerDto.builder()
                .id(banner.getId())
                .subject(banner.getSubject())
                .imageUrl(banner.getImageUrl())
                .link(banner.getLink())
                .openType(banner.getOpenType())
                .sortOrder(banner.getSortOrder())
                .active(banner.isActive())
                .regDt(banner.getRegDt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();
    }
}
