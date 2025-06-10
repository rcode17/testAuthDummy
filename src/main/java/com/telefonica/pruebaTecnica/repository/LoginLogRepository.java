package com.telefonica.pruebaTecnica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.telefonica.pruebaTecnica.model.LoginLog;

@Repository
public interface LoginLogRepository extends JpaRepository<LoginLog, Long> {
}
