package com.springboot.api.service;

import com.springboot.api.data.entity.Report;
import com.springboot.api.data.repository.ReportRepository;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ReportService {
    @Autowired
    private final ReportRepository reportRepository;

    public Report saveReport(Report report)
    {
        Report savedReport = reportRepository.save(report);

        return savedReport;
    }
}
