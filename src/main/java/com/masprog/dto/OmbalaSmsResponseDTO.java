package com.masprog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OmbalaSmsResponseDTO {
    private String id;
    private List<String> numbers;
    private int sent_sms_count;
    private String content;
    private String status;
    private String requested_at;
}
