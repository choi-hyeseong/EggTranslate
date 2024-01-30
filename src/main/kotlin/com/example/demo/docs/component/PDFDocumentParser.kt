package com.example.demo.docs.component

import com.example.demo.docs.component.pdf.PDFConverter
import com.example.demo.docs.dto.DocumentReadResponse
import com.example.demo.docs.dto.DocumentWriteResponse
import java.io.ByteArrayInputStream

class PDFDocumentParser(
    stream: ByteArrayInputStream,
    private val pdfConverter: PDFConverter
) : DocumentParser(stream) {
    //원래는 factory를 parser내에 넣고자 했으나, 순환참조적인 부분도 그렇고, 확장성에서도 매우 큰 단점을 안고 가는거라
    //그냥 WordDocumentParser 객체를 생성하기로 함.
    //실질적인 파싱 로직은 WordDocumentParser에 의존하는 만큼, Composition인 관계도 좋을듯.
    private lateinit var wordDocumentParser: WordDocumentParser

    override suspend fun read(): DocumentReadResponse {
        val response = pdfConverter.convertPdfToDocx(stream)
        wordDocumentParser = WordDocumentParser(response)
        return wordDocumentParser.read()
    }

    override suspend fun write(translate: String, path: String): DocumentWriteResponse {
        return wordDocumentParser.write(translate, path)
    }
}