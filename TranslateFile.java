package com.resoft.ocr_translation.repository;

import com.resoft.ocr_translation.dto.TranslateFileRequestDto;
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
public class TranslateFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "file_id")
    private Long fileId;

    @JoinColumn(name = "translate_result_id")
    private Long translate_id;

    public TranslateFile(TranslateFileRequestDto translateRequestDto) {
        this.id = id;
        this.fileId = translateRequestDto.getFileId();
        this.translate_id = translateRequestDto.getTranslate_id();
    }
}