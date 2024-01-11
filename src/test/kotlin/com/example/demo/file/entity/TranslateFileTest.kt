package com.example.demo.file.entity

import com.example.demo.translate.auto.repository.TranslateFileRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
class TranslateFileTest {

    @Autowired
    lateinit var translateFileRepository: TranslateFileRepository

    @Test
    @Transactional
    fun TEST_SAVE_TRANSLATE_FILE() {

    }
}
