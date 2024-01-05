package com.resoft.ocr_translation.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TranslateFileRequestDto {
    private Long id;
    private Long fileId;
    private Long translate_id;

    public TranslateFileRequestDto(Long id, Long fileId, Long translate_id) {
        this.id = id;
        this.fileId = fileId;
        this.translate_id = translate_id;
    }
}
