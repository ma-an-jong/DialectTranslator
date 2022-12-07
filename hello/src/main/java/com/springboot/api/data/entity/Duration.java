package com.springboot.api.data.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Embeddable
public class Duration {
    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime closeTime;

    public Duration()
    {
        startTime = LocalDateTime.now();
    }

    public void setCloseTime()
    {
        closeTime = LocalDateTime.now();
    }
}
