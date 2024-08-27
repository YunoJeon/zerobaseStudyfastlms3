package com.zerobase.fastlms.admin.service;

import com.zerobase.fastlms.admin.dto.BannerDto;
import com.zerobase.fastlms.admin.entity.Banner;
import com.zerobase.fastlms.admin.model.BannerInput;
import com.zerobase.fastlms.admin.repository.BannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BannerServiceImpl implements BannerService {

    private final BannerRepository bannerRepository;

    @Override
    public List<BannerDto> list() {
        return bannerRepository.findAllByOrderBySortOrderAsc().stream()
                .map(BannerDto::of)
                .collect(Collectors.toList());
    }

    @Override
    public boolean add(BannerInput parameter) {
        Banner banner = Banner.builder()
                .subject(parameter.getSubject())
                .imageUrl(parameter.getImageUrl())
                .link(parameter.getLink())
                .openType(parameter.getOpenType())
                .sortOrder(parameter.getSortOrder())
                .active(parameter.isActive())
                .regDt(LocalDateTime.now())
                .build();

        System.out.println("Banner isActive before save: " + banner.isActive());
        bannerRepository.save(banner);
        return true;
    }

    @Override
    public boolean update(BannerInput parameter) {
        Banner banner = bannerRepository.findById(parameter.getId())
                .orElseThrow(() -> new IllegalStateException("Invalid banner ID"));

        banner.setSubject(parameter.getSubject());
        if (parameter.getImageUrl() != null && !parameter.getImageUrl().isEmpty()) {
            banner.setImageUrl(parameter.getImageUrl());
        }
        banner.setLink(parameter.getLink());
        banner.setOpenType(parameter.getOpenType());
        banner.setSortOrder(parameter.getSortOrder());
        banner.setActive(parameter.isActive());
        banner.setRegDt(LocalDateTime.now());

        bannerRepository.save(banner);
        return true;
    }

    @Override
    public boolean delete(Long id) {
        bannerRepository.deleteById(id);
        return true;
    }

    @Override
    public BannerDto getById(Long id) {
        return bannerRepository.findById(id)
                .map(BannerDto::of)
                .orElse(null);
    }

    @Override
    public List<BannerDto> getActiveBanners() {
        return bannerRepository.findByActiveTrueOrderBySortOrderAsc().stream()
                .map(BannerDto::of)
                .collect(Collectors.toList());
    }
}
