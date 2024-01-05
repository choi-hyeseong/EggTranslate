package com.resoft.ocr_translation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TranslateRequestDto {
    private Long id;
    private Long userId;
    private Long translate_result_Id;
    private Long childId;
    private String status;

    public TranslateRequestDto(Long id, Long userId, Long translate_result_Id, Long childId, String status) {
        this.id = id;
        this.userId = userId;
        this.translate_result_Id = translate_result_Id;
        this.childId = childId;
        this.status = status;
    }
}
