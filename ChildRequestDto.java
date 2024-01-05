package com.resoft.ocr_translation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ChildRequestDto {
    private Long id;
    private Long userId;
    private String name;
    private String phone;
    private String school;
    private int grade;
    private String className;

    public ChildRequestDto(Long id, Long userId, String name, String phone, String school, int grade, String className) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.school = school;
        this.grade = grade;
        this.className = className;
    }
}
