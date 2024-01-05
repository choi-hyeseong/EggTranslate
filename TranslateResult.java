package com.resoft.ocr_translation.repository;

import com.resoft.ocr_translation.dto.TranslateResultRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class TranslateResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id")
    private Long userId;

    @Column(nullable = false)
    private String origin;

    @Column(nullable = false)
    private String translate;

    @Column(nullable = false, length = 3)
    private String from;

    @Column(nullable = false, length = 3)
    private String to;

    @OneToMany(mappedBy = "translateresult")
    @Column(nullable = false)
    private List<TranslateFile> translateFiles = new ArrayList<>();

    public TranslateResult(TranslateResultRequestDto translateResultRequestDto) {
        this.id = translateResultRequestDto.getId();
        this.userId = translateResultRequestDto.getUserId();
        this.origin = translateResultRequestDto.getOrigin();
        this.translate = translateResultRequestDto.getTranslate();
        this.from = translateResultRequestDto.getFrom();
        this.to = translateResultRequestDto.getTo();
    }
}
