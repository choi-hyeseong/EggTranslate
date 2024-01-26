package com.example.demo.docs.component

import com.example.demo.docs.dto.DocumentReadResponse
import com.example.demo.docs.dto.DocumentWriteResponse
import com.example.demo.docs.exception.DocumentException
import kr.dogfoot.hwplib.`object`.HWPFile
import kr.dogfoot.hwplib.`object`.bodytext.control.Control
import kr.dogfoot.hwplib.`object`.bodytext.control.ControlTable
import kr.dogfoot.hwplib.`object`.bodytext.control.ControlType
import kr.dogfoot.hwplib.`object`.bodytext.paragraph.Paragraph
import kr.dogfoot.hwplib.reader.HWPReader
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream

//빈 사용 대신 팩토리가 하나 필요할듯?
class HwpDocumentParser(file: MultipartFile) : DocumentParser(file) {

    private lateinit var hwpFile: HWPFile
    private val paragraphs : MutableList<Paragraph> = mutableListOf()

    override suspend fun read(): DocumentReadResponse {
        try {
            hwpFile = HWPReader.fromInputStream(ByteArrayInputStream(file.bytes))
            return readFile(hwpFile)
        }
        catch (e: Exception) {
            throw DocumentException(e.localizedMessage)
        }
    }

    override suspend fun write(translate: String, path: String): DocumentWriteResponse {
        // TODO 여백 있는 줄 번역기가 인식못하므로 지우기.
        TODO("Not yet implemented")
    }

    private fun readFile(hwpFile : HWPFile) : DocumentReadResponse {
        val builder : StringBuilder = StringBuilder()
        hwpFile.bodyText.sectionList.forEach { section ->
            section.paragraphs.forEach {
                val paragraphResponse = parseParagraph(it).plus("\n")
                builder.append(paragraphResponse)
            }
        }
        return DocumentReadResponse(true, builder.toString())
    }

    private fun parseParagraph(paragraph: Paragraph) : String {
        //한 문단을 파싱하는 경우.
        if (paragraph.normalString.isNotBlank()) {
            //문단내에 내용이 있을때
            paragraphs.add(paragraph)
            return paragraph.normalString
        }
        else
            return handleControl(paragraph.controlList)
    }

    private fun handleControl(controls : Iterable<Control>?) : String {
        val builder : StringBuilder = StringBuilder()
        //컨트롤이 없는경우
        if (controls == null)
            return ""


        controls.forEach { control ->
            if (control.type == ControlType.Table) {
                //여러개의 테이블이 한 문단에 존재할 수 있음.
                val tableResponse = handleTable(control as ControlTable)
                builder.append(tableResponse) //테이블 파싱한경우 줄바꿈 포함해서 추가.
            }
        }
        return builder.toString()
    }

    private fun handleTable(table : ControlTable) : String {
        val builder : StringBuilder = StringBuilder()
        table.rowList.forEach { row ->
            row.cellList.forEach { cell ->
                cell.paragraphList.forEach { paragraph ->
                    //한 문단당 줄바꿈 추가. 이것도 맨 처음이랑 유사한 기능이라 묶는것도 좋을듯
                    builder.append(parseParagraph(paragraph).plus("\n"))
                }
                builder.append("\t") //한 셀이 끝났을때 탭 넣기
            }
        }
        return builder.toString()
    }


}