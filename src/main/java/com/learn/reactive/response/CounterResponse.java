package com.learn.reactive.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CounterResponse {

    private boolean success;
    private String responseMassage;
    private String responseCode;
    private Long count;
}
