package com.guru.apigateway.dto;

import lombok.*;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ApiKey {

    private String key;
    private List<String> services;
}
