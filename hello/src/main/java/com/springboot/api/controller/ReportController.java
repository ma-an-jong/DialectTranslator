package com.springboot.api.controller;

import com.springboot.api.data.entity.Report;
import com.springboot.api.service.ReportService;
import com.springboot.api.service.dto.ReportRequestDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportController {

    @Autowired
    private ReportService reportService;

    @ApiOperation(value="사용자 건의 사항",notes = "사용자 의견 수집")
    @PostMapping(value = "/api/report")
    public ResponseEntity<String> enrollReport(@ApiParam @RequestBody ReportRequestDTO reportRequestDTO)
    {
        try
        {
            String content  = reportRequestDTO.getContent();
            System.out.println("report: " + content);
            reportService.saveReport(new Report(content));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("의견 접수에 실패하였습니다.");
        }
        return ResponseEntity.status(HttpStatus.OK).body("의견이 접수되었습니다.");
    }
}
