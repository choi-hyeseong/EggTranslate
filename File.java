package com.resoft.ocr_translation.repository;

import com.resoft.ocr_translation.dto.FileRequestDto;
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
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_userId")
    private int userId;

    @Column(nullable = false, length = 255)
    private String origin_name;

    @Column(nullable = false, length = 255)
    private String save_name;

    @OneToMany(mappedBy = "file")
    private List<TranslateFile> translate_files = new ArrayList<>();

    public File(FileRequestDto fileRequestDto) {
        this.id = fileRequestDto.getId();
        this.userId = fileRequestDto.getUserId();
        this.origin_name = fileRequestDto.getOrigin_name();
        this.save_name = fileRequestDto.getSave_name();
    }
}
