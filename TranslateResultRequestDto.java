package com.resoft.ocr_translation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TranslateResultRequestDto {

    private Long id;
    private Long userId;
    private String origin;
    private String translate;
    private String from;
    private String to;

    public TranslateResultRequestDto(Long id, Long userId, String origin, String translate, String from, String to) {
        this.id = id;
        this.userId = userId;
        this.origin = origin;
        this.translate = translate;
        this.from = from;
        this.to = to;
    }
}
