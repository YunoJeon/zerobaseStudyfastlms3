package com.zerobase.fastlms.admin.controller;

import com.zerobase.fastlms.admin.dto.BannerDto;
import com.zerobase.fastlms.admin.model.BannerInput;
import com.zerobase.fastlms.admin.service.BannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/admin/banner")
@RequiredArgsConstructor
public class AdminBannerController {

    private final BannerService bannerService;

    @GetMapping("/list.do")
    public String list(Model model) {
        List<BannerDto> banners = bannerService.list();
        model.addAttribute("list", banners);
        model.addAttribute("totalCount", banners.size());
        return "admin/banner/list";
    }

    @GetMapping("/add.do")
    public String add(Model model) {
        model.addAttribute("editMode", false);
        model.addAttribute("detail", new BannerDto());
        return "admin/banner/add";
    }

    @PostMapping("/add.do")
    public String addSubmit(@ModelAttribute BannerInput parameter, @RequestParam MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                String uploadDir = new File("src/main/resources/static/uploads").getAbsolutePath() + File.separator;
                File uploadDirFile = new File(uploadDir);

                if (!uploadDirFile.exists()) {
                    uploadDirFile.mkdirs();
                }

                String fileName = file.getOriginalFilename().replaceAll(" ", "_");
                String filePath = uploadDir + fileName;
                File targetFile = new File(filePath);

                file.transferTo(targetFile);

                String imageUrl = "/uploads/" + fileName.replaceAll(" ", "_");
                parameter.setImageUrl(imageUrl);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("BannerInput isActive: " + parameter.isActive());

        bannerService.add(parameter);
        return "redirect:/admin/banner/list.do";
    }

    @GetMapping("/edit.do")
    public String edit(@RequestParam("id") Long id, Model model) {
        BannerDto banner = bannerService.getById(id);
        model.addAttribute("detail", banner);
        model.addAttribute("editMode", true);
        return "admin/banner/add";
    }

    @PostMapping("/edit.do")
    public String editSubmit(@ModelAttribute BannerInput parameter, @RequestParam(required = false) MultipartFile file) {
        BannerDto existing = bannerService.getById(parameter.getId());
        if (existing != null) {
            String existingImageUrl = existing.getImageUrl();

            if (file != null && !file.isEmpty()) {
                try {
                    String uploadDir = new File("src/main/resources/static/uploads").getAbsolutePath() + File.separator;
                    File uploadDirFile = new File(uploadDir);

                    if (!uploadDirFile.exists()) {
                        uploadDirFile.mkdirs();
                    }

                    String fileName = file.getOriginalFilename().replaceAll(" ", "_");
                    String filePath = uploadDir + fileName;
                    File targetFile = new File(filePath);

                    file.transferTo(targetFile);

                    String imageUrl = "/uploads/" + fileName.replaceAll(" ", "_");
                    parameter.setImageUrl(imageUrl);

                    if (existingImageUrl != null && !existingImageUrl.equals(imageUrl)) {
                        Path path = Paths.get("src/main/resources/static/uploads/" + existingImageUrl);
                        Files.deleteIfExists(path);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                parameter.setImageUrl(existingImageUrl);
            }
            bannerService.update(parameter);
        }
        return "redirect:/admin/banner/list.do";
    }

    @PostMapping("/delete.do")
    public String delete(@RequestParam("idList") List<Long> idList) {
        for (Long id : idList) {
            bannerService.delete(id);
        }
        return "redirect:/admin/banner/list.do";
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxSizeException(Model model) {
        model.addAttribute("message", "파일 크기가 너무 큽니다. 최대 10MB 까지 업로드 가능합니다.");
        return "common/error";
    }
}
