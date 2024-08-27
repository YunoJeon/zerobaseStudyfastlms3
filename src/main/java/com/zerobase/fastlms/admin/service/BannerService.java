package com.zerobase.fastlms.admin.service;

import com.zerobase.fastlms.admin.dto.BannerDto;
import com.zerobase.fastlms.admin.model.BannerInput;

import java.util.List;

public interface BannerService {
    List<BannerDto> list();

    boolean add(BannerInput parameter);

    boolean update(BannerInput parameter);

    boolean delete(Long id);

    BannerDto getById(Long id);

    List<BannerDto> getActiveBanners();
}
