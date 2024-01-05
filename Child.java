package com.resoft.ocr_translation.repository;

import com.resoft.ocr_translation.dto.ChildRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor // 기본 생성자를 만들어줌.
@Entity
public class Child {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_userId")
    private Long userId;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 35)
    private String phone;

    @Column(nullable = false, length = 25)
    private String school;

    @Column(nullable = false)
    private int grade;

    @Column(nullable = false, length = 15)
    private String className;

    public Child(ChildRequestDto childRequestDto) {
        this.id = childRequestDto.getId();
        this.userId = childRequestDto.getUserId();
        this.name = childRequestDto.getName();
        this.phone = childRequestDto.getPhone();
        this.school = childRequestDto.getSchool();
        this.grade = childRequestDto.getGrade();
        this.className = childRequestDto.getClassName();
    }

}
