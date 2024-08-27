package com.zerobase.fastlms.configuration;

import com.zerobase.fastlms.loginHistory.service.LoginHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final LoginHistoryService loginHistoryService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String email = authentication.getName();
        String connIp = request.getRemoteAddr();
        String connUa = request.getHeader("User-Agent");

        loginHistoryService.recordLogin(email, connIp, connUa);

        response.sendRedirect("/");
    }
}
