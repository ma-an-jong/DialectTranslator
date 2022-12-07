package com.springboot.api.service;

import com.springboot.api.data.entity.Log;
import com.springboot.api.data.repository.LogRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class LogService {

    @Autowired
    private final LogRepository logRepository;

    public Log saveLog(Log log)
    {
        Log savedLog = logRepository.save(log);

        return savedLog;
    }

}
