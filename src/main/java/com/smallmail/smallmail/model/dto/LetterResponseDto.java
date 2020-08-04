package com.smallmail.smallmail.model.dto;

import lombok.Data;

@Data
public class LetterResponseDto {
    private Long id;
    private String theme;
    private String inOrOut;
    private String time;
}
