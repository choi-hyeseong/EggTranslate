package com.example.demo.image.service

import com.example.demo.file.exception.FileException
import com.example.demo.file.repository.ConvertFileRepository
import com.example.demo.file.util.FileUtil
import com.example.demo.file.dto.ConvertFileDTO
import com.example.demo.ocr.component.ocr.model.Area
import com.example.demo.ocr.component.ocr.model.Paragraph
import com.example.demo.member.user.dto.UserDto
import jakarta.transaction.Transactional
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.awt.Color
import java.awt.Font
import java.awt.Graphics
import java.awt.geom.Rectangle2D
import kotlin.math.min

@Service
class ConvertService(
    @Value("\${image-path}")
    val path: String,
    val convertFileRepository: ConvertFileRepository
) {

    @Transactional
    suspend fun getFile(fileId: Long): Resource {
        return FileUtil.convertFileToResource(findFileById(fileId))
    }

    @Transactional
    suspend fun findFileById(fileId: Long): ConvertFileDTO {
        return ConvertFileDTO(
            convertFileRepository.findById(fileId).orElseThrow { FileException("존재 하지 않는 변환된 이미지 파일입니다.") })
    }

    suspend fun convertFile(file: MultipartFile, paragraph: List<Paragraph>, userDto: UserDto?): ConvertFileDTO {
        return withContext(Dispatchers.IO) {
            val image = FileUtil.cloneImage(file) //이미지 복제 (새로운 이미지 파일)
            val graphic = image.graphics

            paragraph.forEach { markingContent(graphic, it) } //기존 내용 제거
            paragraph.forEach { writeContent(graphic, it) } //다 지운후 내용 작성하기.

            graphic.dispose()
            val saveName = "${System.currentTimeMillis()}.png"
            val savePath = path.plus("/convert").plus("/$saveName")
            FileUtil.saveImage(image, savePath)

            ConvertFileDTO(null, saveName, savePath, userDto)
        }
    }

    private suspend fun writeContent(graphic: Graphics, paragraph: Paragraph) {
        val x = paragraph.area.min.x.toInt()
        val y = paragraph.area.min.y.toInt()
        val width = paragraph.area.width.toInt()
        val height = paragraph.area.height.toInt()
        val font = findSuggestedFontInArea(graphic, paragraph.area, paragraph.content)

        graphic.color = Color.lightGray //테두리 그리기
        graphic.drawRect(x, y, width, height)
        //글씨 작성하기
        writeText(graphic, font, paragraph.content, paragraph.area)
    }

    private suspend fun markingContent(graphic: Graphics, paragraph: Paragraph) {
        val x = paragraph.area.min.x.toInt()
        val y = paragraph.area.min.y.toInt()
        val width = paragraph.area.width.toInt()
        val height = paragraph.area.height.toInt()
        graphic.color = Color.white //지우기
        graphic.fillRect(x, y, width, height)
    }

    fun findSuggestedFontInArea(graphics: Graphics, area: Area, content: String): FontData {
        val width = area.width.toInt()
        val height = area.height.toInt()
        var font = Font("Arial", Font.PLAIN, 50)
        var data = findFontData(graphics, font, width, height, content)
        for (i in 0..50000) {
            data = findFontData(graphics, font, width, height, content)
            val ascentHeight = height - graphics.getFontMetrics(font).ascent
            if (data.maxLine * data.charHeight <= ascentHeight || font.size == 1)
                break

            font = Font(graphics.font.name, graphics.font.style, font.size - 1)
        }
        return data
    }

    fun findFontData(g: Graphics, font: Font, width: Int, height: Int, content: String): FontData {
        val metrics = g.getFontMetrics(font)
        val bounds: Rectangle2D = metrics.getStringBounds(content, g)
        // 10보다 작을경우 너비를 좀 늘려서 줄바꿈을 생기게 함
        var fontWidth =
            if (font.size <= 10)
                ((bounds.width.toInt() / content.length) * 1.3).toInt()
            else
                bounds.width.toInt() / content.length
        if (fontWidth == 0)
            fontWidth = 1

        var maxWidth = width / (fontWidth) //최대 들어갈 수 있는 char의 수
        if (maxWidth == 0)
            maxWidth = 1

        val fontHeight = bounds.height.toInt()
        val contentLine = content.length / maxWidth
        return FontData(font, maxWidth, contentLine, fontWidth, fontHeight)
    }


    fun writeText(g: Graphics, fontData: FontData, content: String, area: Area) {
        var index = 0
        val startX = area.min.x.toInt()
        var startY = area.min.y.toInt()
        g.color = Color.black
        if (fontData.font.size < 9)
            g.font = Font(g.font.name, Font.PLAIN, 9)
        else
            g.font = Font(g.font.name, Font.PLAIN, 12)

        val charPerLine = findFontData(
            g,
            g.font,
            area.width.toInt() - (area.width.toInt() * 0.15).toInt(),
            area.height.toInt(),
            content
        ).maxCharPerLine

        startY += g.fontMetrics.ascent //꼭짓점에서 폰트의 중간 좌표로 이동.

        for (i in 0..10000) {
            if (index >= content.length) //작성할 string이 없으면 break
                break

            val next = min(index + charPerLine, content.length)

            val drawString = content.substring(index, next)
            if (startY + fontData.charHeight > area.max.y) {
                g.drawString("$drawString.", startX, startY)
                break
            } else
                g.drawString(drawString, startX, startY)

            startY += fontData.charHeight //다음 줄로 이동
            index = next
        }
    }
}

data class FontData(
    val font: Font,
    val maxCharPerLine: Int,
    val maxLine: Int,
    val charWidth: Int,
    val charHeight: Int
)