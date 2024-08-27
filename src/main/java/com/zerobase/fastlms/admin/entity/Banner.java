package com.zerobase.fastlms.admin.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Banner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject; // 배너명
    private String imageUrl; // 이미지 경로
    private String link; // 링크 주소
    private String openType; // 오픈 방법
    private int sortOrder; // 정렬 순서
    private boolean active; // 배너 공개 여부

    private LocalDateTime regDt; // 등록일
}
