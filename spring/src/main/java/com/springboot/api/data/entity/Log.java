package com.springboot.api.data.entity;
import com.springboot.api.service.dto.TranslateRequestDTO;
import com.springboot.api.service.dto.TranslateResponseDTO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="log")
public class Log
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String dialect;

    private Integer label;

    @Column(nullable = false)
    private String region;

    @Column(nullable = false)
    private String result;

    @Embedded
    private Duration duration;

    public Log(TranslateRequestDTO requestDTO)
    {
        this.dialect = requestDTO.getDialect();
        this.label = requestDTO.getLabel();
        this.duration = new Duration();
    }

    public void setResult(TranslateResponseDTO responseDTO)
    {
        this.result = responseDTO.getStandard_form();
        this.region = responseDTO.getRegion();
        duration.setCloseTime();
    }

}
