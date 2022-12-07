package com.springboot.api.data.repository;

import com.springboot.api.data.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report,Long> {
}
