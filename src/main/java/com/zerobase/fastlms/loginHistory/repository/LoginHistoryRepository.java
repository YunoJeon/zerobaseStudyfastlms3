package com.zerobase.fastlms.loginHistory.repository;

import com.zerobase.fastlms.loginHistory.entity.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {
    LoginHistory findTopByEmailOrderByLoginDTDesc(String email);
    List<LoginHistory> findByEmailOrderByLoginDTDesc(String email);
}
