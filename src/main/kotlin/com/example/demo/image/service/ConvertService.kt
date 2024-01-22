package com.example.demo.image.service

import com.example.demo.file.exception.FileException
import com.example.demo.file.repository.ConvertFileRepository
import com.example.demo.file.util.FileUtil
import com.example.demo.image.dto.ConvertFileDTO
import com.example.demo.ocr.component.ocr.model.Area
import com.example.demo.ocr.component.ocr.model.Paragraph
import com.google.cloud.vision.v1p4beta1.Image
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
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.File
import javax.imageio.ImageIO

@Service
class ConvertService(
    @Value("\${image-path}")
    val path : String,
    val convertFileRepository: ConvertFileRepository
) {

    //https://wlsufld.tistory.com/112 참조
    // TODO 마무리 하기.
    @Transactional
    suspend fun getFile(fileId: Long): Resource {
        val fileDto = ConvertFileDTO(convertFileRepository.findById(fileId).orElseThrow {FileException("존재 하지 않는 변환된 이미지 파일입니다.")})
        return FileUtil.convertFileToResource(fileDto)
    }

    suspend fun convertFile(file: MultipartFile, paragraph: List<Paragraph>): ConvertFileDTO {
        return withContext(Dispatchers.IO) {
            val image = ImageIO.read(ByteArrayInputStream(file.bytes))
            val cloneImage = BufferedImage(image.width, image.height, java.awt.Image.SCALE_FAST)
            val graphic = cloneImage.graphics

            graphic.drawImage(image, 0,0, null) //이미지 그리기 (복붙)
            for (para in paragraph) {
                graphic.color = Color.white
                graphic.fillRect(
                    para.area.min.x.toInt(), para.area.min.y.toInt(),
                    para.area.width.toInt(), para.area.height.toInt()
                )
            }
            for (para in paragraph) {
                graphic.color = Color.lightGray
                graphic.drawRect(
                    para.area.min.x.toInt(), para.area.min.y.toInt(),
                    para.area.width.toInt(), para.area.height.toInt()
                )
                val font = findFont(graphic, para.area, para.content)
                writeText(graphic, font, para.content, para.area)
            }

            graphic.dispose()
            val saveName = "${System.currentTimeMillis()}.png"
            val resultFile = File(path.plus("/convert").plus(saveName))
            if (!resultFile.parentFile.exists())
                resultFile.parentFile.mkdir()

            ImageIO.write(cloneImage, "png", resultFile)

            ConvertFileDTO(null, saveName, resultFile.path)
        }
    }

    fun findFont(graphics: Graphics, area: Area, content: String): FontData {
        var maxWidth: Int = 1
        var fontWidth: Int = 1
        var fontHeight: Int = 1
        val width = area.width.toInt()
        val height = area.height
        var font = Font("Arial", Font.PLAIN, 50)

        for (i in 0..50000) {
            val metrics = graphics.getFontMetrics(font)
            val bounds: Rectangle2D = metrics.getStringBounds(content, graphics)
            val copyHeight = height - metrics.ascent

            fontWidth = (bounds.width.toInt() / content.length)
            fontHeight = bounds.height.toInt()
            maxWidth = width / (fontWidth) //최대 들어갈 수 있는 char의 수
            if (maxWidth == 0)
                maxWidth = 1

            val contentLine = content.length / maxWidth
            if (contentLine * fontHeight <= copyHeight || font.size == 1)
                break

            font = Font(graphics.font.name, graphics.font.style, font.size - 1)
        }
        return FontData(font, maxWidth, fontWidth, fontHeight)
    }

    fun findCharPerLine(g: Graphics, font: Font, width: Int, content: String): Int {
        val metrics = g.getFontMetrics(font)
        val bounds: Rectangle2D = metrics.getStringBounds(content, g)
        val fontWidth = (bounds.width / content.length).toLong()
        return (width / (fontWidth)).toInt() //최대 들어갈 수 있는 char의 수
    }


    fun writeText(g: Graphics, fontData: FontData, content: String, area: Area) {
        var index = 0
        val startX = area.min.x.toInt()
        var startY = area.min.y.toInt()
        g.color = Color.black
        g.font = Font(fontData.font.name, Font.PLAIN, fontData.font.size - 3)
        if (g.font.size < 9)
            g.font = Font(g.font.name, Font.BOLD, 9)

        startY += g.fontMetrics.ascent //꼭짓점에서 폰트의 중간 좌표로 이동.
        for (i in 0..10000) {
            if (index >= content.length)
                break
            var nextChar = fontData.maxChar
            if (g.font.size == 7)
                nextChar = findCharPerLine(g, g.font, area.width.toInt(), content) - 2 //적당한 줄바꿈 위한 - 2

            val next =
                if (index + nextChar > content.length)
                    content.length.toLong()
                else index + nextChar

            val drawString = content.substring(index, next.toInt())
            g.drawString(drawString, startX, startY)

            startY += fontData.charHeight //다음 줄로 이동
            index = next.toInt()
        }
    }
}

data class FontData(val font: Font, val maxChar: Int, val charWidth: Int, val charHeight: Int)