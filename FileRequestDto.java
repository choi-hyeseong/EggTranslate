package com.resoft.ocr_translation.dto;

import com.resoft.ocr_translation.repository.File;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FileRequestDto {
    private Long id;
    private int userId;
    private String origin_name;
    private String save_name;

    public FileRequestDto(Long id, int userId, String origin_name, String save_name) {
        this.id = id;
        this.userId = userId;
        this.origin_name = origin_name;
        this.save_name = save_name;
    }
}
