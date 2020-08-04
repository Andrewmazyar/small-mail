package com.smallmail.smallmail.model.dto;

import lombok.Data;

@Data
public class LetterDetailDto {
    private String user;
    private String theme;
    private String body;
    private String forWhom;
}
