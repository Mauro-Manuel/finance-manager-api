package com.masprog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class OmbalaSmsRequestDTO {
    private String message;
    private String from;
    private String to;
}
