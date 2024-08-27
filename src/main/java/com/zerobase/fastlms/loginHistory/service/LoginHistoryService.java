package com.zerobase.fastlms.loginHistory.service;

import com.zerobase.fastlms.loginHistory.dto.LoginHistoryDto;
import com.zerobase.fastlms.loginHistory.entity.LoginHistory;
import com.zerobase.fastlms.loginHistory.repository.LoginHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoginHistoryService {

    private final LoginHistoryRepository loginHistoryRepository;

    public void recordLogin(String email, String connIp, String connUa) {
        LoginHistory loginHistory = LoginHistory.builder()
                .email(email)
                .loginDT(LocalDateTime.now())
                .connIp(connIp)
                .cnnUa(connUa)
                .build();

        loginHistoryRepository.save(loginHistory);
    }
    public LocalDateTime findLastLoginDateByEmail(String email) {
        LoginHistory loginHistory = loginHistoryRepository.findTopByEmailOrderByLoginDTDesc(email);
        return loginHistory != null ? loginHistory.getLoginDT() : null;

    }

    public List<LoginHistoryDto> findLoginHistoryByEmail(String email) {
        List<LoginHistory> histories = loginHistoryRepository.findByEmailOrderByLoginDTDesc(email);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return histories.stream()
                .map(history -> new LoginHistoryDto(
                        history.getId(),
                        history.getEmail(),
                        history.getLoginDT().format(formatter),
                        history.getConnIp(),
                        history.getCnnUa()
                ))
                .collect(Collectors.toList());
    }
}
