package com.example.demo.docs.component

import com.example.demo.docs.dto.DocumentReadResponse
import com.example.demo.docs.dto.DocumentWriteResponse
import com.example.demo.docs.exception.DocumentException
import com.example.demo.logger
import org.apache.poi.xwpf.usermodel.XWPFDocument
import org.apache.poi.xwpf.usermodel.XWPFParagraph
import org.apache.poi.xwpf.usermodel.XWPFTable
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream
import java.io.FileOutputStream

class WordDocumentParser(stream: ByteArrayInputStream) : DocumentParser(stream) {

    private val logger = logger()
    private lateinit var document: XWPFDocument
    private val paragraphs: MutableList<XWPFParagraph> = mutableListOf()

    override suspend fun read(): DocumentReadResponse {
        try {
            document = XWPFDocument(stream)
            document.paragraphs.forEach(this::parseParagraph)
            document.tables.forEach(this::parseTable)
        } catch (e: Exception) {
            throw DocumentException(e.localizedMessage)
        }
        return DocumentReadResponse(paragraphs.joinToString(separator = "\n") { it.text })
    }

    override suspend fun write(translate: String, path: String): DocumentWriteResponse {
        translate.split("\n").forEachIndexed { index, splitText ->
            if (paragraphs.size > index)
                setParagraphText(paragraphs[index], splitText)
            else
                logger.warn("Can't parse $splitText index of $index")
        }
        document.write(FileOutputStream("C:\\Users\\bd284\\OneDrive\\바탕 화면\\result1.docx"))
        return DocumentWriteResponse(path, paragraphs.joinToString(separator = "\n") { it.text }, translate)
    }


    private fun setParagraphText(paragraph: XWPFParagraph, content: String) {
        if (paragraph.runs.isNotEmpty()) {
            val run = paragraph.runs.first()
            run.setText(content, 0)
            paragraph.runs.toMutableList().forEachIndexed { runIndex, otherRun ->
                if (runIndex != 0) //first제외하고 다 지움.
                    otherRun.setText("", 0)
            }
        }
    }


    private fun parseParagraph(paragraph: XWPFParagraph) {
        if (paragraph.text.isNotBlank()) { //텍스트가 비어 있지 않은경우
            this.paragraphs.add(paragraph)
        }
    }


    //테이블을 recursive하게 파싱.
    private fun parseTable(table: XWPFTable) {
        table.rows.forEach { row ->
            row.tableCells.forEach { cell ->
                cell.paragraphs.forEach(this::parseParagraph)
                if (cell.tables != null)
                    cell.tables.forEach(this::parseTable)
            }
        }
    }


}