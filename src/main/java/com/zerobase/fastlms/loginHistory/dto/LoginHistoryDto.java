package com.zerobase.fastlms.loginHistory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginHistoryDto {
    private Long id;
    private String email;
    private String loginDT;
    private String connIp;
    private String cnnUa;
}
