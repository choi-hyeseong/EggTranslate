package com.resoft.ocr_translation.repository;

import com.resoft.ocr_translation.dto.TranslateRequestDto;
import com.resoft.ocr_translation.dto.TranslateResultRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class TranslateRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name="user_id")
    private Long userId;

    @JoinColumn(name = "translator_id")
    private Long translatorId;

    @JoinColumn(name = "translate_result_id")
    private Long translate_result_Id;

    @JoinColumn(name = "child_id")
    private Long childId;

    @Column(nullable = false, length = 10)
    private String status;

    public TranslateRequest(TranslateRequestDto translateRequestDto) {
        this.id = translateRequestDto.getId();
        this.userId = translateRequestDto.getUserId();
        this.translatorId = translateRequestDto.getTranslate_result_Id();
        this.childId = translateRequestDto.getChildId();
        this.status = translateRequestDto.getStatus();
    }
}
