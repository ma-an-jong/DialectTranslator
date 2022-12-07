package com.springboot.api.data.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@Table(name="report")
@Entity
public class Report {

    @Id @GeneratedValue
    private Long id;

    private String content;

    public Report(String content)
    {
        this.content=content;
    }
}
