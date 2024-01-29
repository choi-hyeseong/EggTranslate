package com.example.demo.docs.component

import com.example.demo.docs.dto.DocumentReadResponse
import com.example.demo.docs.dto.DocumentWriteResponse
import com.example.demo.docs.exception.DocumentException
import com.example.demo.docs.util.HWPUtil
import kr.dogfoot.hwplib.`object`.HWPFile
import kr.dogfoot.hwplib.`object`.bodytext.control.Control
import kr.dogfoot.hwplib.`object`.bodytext.control.ControlTable
import kr.dogfoot.hwplib.`object`.bodytext.control.ControlType
import kr.dogfoot.hwplib.`object`.bodytext.paragraph.Paragraph
import kr.dogfoot.hwplib.reader.HWPReader
import kr.dogfoot.hwplib.writer.HWPWriter
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream


//빈 사용 대신 팩토리가 하나 필요할듯?
class HwpDocumentParser(file: MultipartFile) : DocumentParser(file) {

    private lateinit var hwpFile: HWPFile
    private val paragraphs: MutableList<Paragraph> = mutableListOf()

    override suspend fun read(): DocumentReadResponse {
        try {
            hwpFile = HWPReader.fromInputStream(ByteArrayInputStream(file.bytes))
            return readFile(hwpFile)
        } catch (e: Exception) {
            throw DocumentException(e.localizedMessage)
        }
    }

    override suspend fun write(translate: String, path: String): DocumentWriteResponse {
        translate.split("\n").forEachIndexed { index, translateString ->
            if (paragraphs.size > index)
                HWPUtil.setParagraphString(paragraphs[index], translateString)
        }
        HWPWriter.toFile(hwpFile, "C:\\Users\\bd284\\OneDrive\\바탕 화면\\convert.hwp")

        return DocumentWriteResponse("", "", translate)
    }

    private fun readFile(hwpFile: HWPFile): DocumentReadResponse {
        hwpFile.bodyText.sectionList.forEach { section ->
            section.paragraphs.forEach {
                parseParagraph(it)
            }
        }
        return DocumentReadResponse(paragraphs.joinToString(separator = "\n") { it.normalString })
    }

    private fun parseParagraph(paragraph: Paragraph) {
        //한 문단을 파싱하는 경우.
        if (paragraph.normalString.isNotBlank()) {
            //문단내에 내용이 있을때
            paragraphs.add(paragraph)
        } else
            handleControl(paragraph.controlList)
    }

    private fun handleControl(controls: Iterable<Control>?) {
        //컨트롤이 없는경우
        if (controls == null)
            return


        controls.forEach { control ->
            if (control.type == ControlType.Table) {
                //여러개의 테이블이 한 문단에 존재할 수 있음.
                handleTable(control as ControlTable)
            }
        }
    }

    private fun handleTable(table: ControlTable) {
        table.rowList.forEach { row ->
            row.cellList.forEach { cell ->
                cell.paragraphList.forEach { paragraph ->
                    //한 문단당 줄바꿈 추가. 이것도 맨 처음이랑 유사한 기능이라 묶는것도 좋을듯
                    parseParagraph(paragraph)
                }
            }
        }
    }


}