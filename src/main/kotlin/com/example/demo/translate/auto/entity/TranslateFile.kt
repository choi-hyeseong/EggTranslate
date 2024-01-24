package com.example.demo.translate.auto.entity

import com.example.demo.common.database.converter.JsonVocaFlatter
import com.example.demo.common.database.converter.StringFlatter
import com.example.demo.file.entity.ConvertFile
import com.example.demo.file.entity.File
import com.example.demo.voca.dto.VocaResponseDTO
import com.example.demo.voca.entity.Voca
import jakarta.persistence.*

@Entity
class TranslateFile(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "convertFile_id")
    var convertFile : ConvertFile?,

    @ManyToOne
    @JoinColumn(name = "file_id")
    var file: File,

    @Column(nullable = false, columnDefinition = "TEXT")
    var origin: String,

    @Column(nullable = false, columnDefinition = "TEXT")
    var translate: String,

    @Column(nullable = false, length = 10)
    var fromLang: String,

    @Column(nullable = false, length = 10)
    var toLang: String,

    @Column(columnDefinition = "TEXT")
    @Convert(converter = JsonVocaFlatter::class) //누가 수정할지는 모르겠지만 미안해오..
    var voca: MutableList<VocaResponseDTO>

)