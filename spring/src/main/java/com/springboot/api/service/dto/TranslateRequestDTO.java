package com.springboot.api.service.dto;


import lombok.*;


@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TranslateRequestDTO {

    private String dialect;
    private Integer label;

}
