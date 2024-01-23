package com.example.demo.file.util

import com.drew.imaging.ImageMetadataReader
import com.drew.metadata.exif.ExifIFD0Directory
import com.example.demo.file.dto.AbstractFileDTO
import com.example.demo.file.dto.FileDTO
import com.example.demo.file.exception.FileException
import com.example.demo.logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.core.io.InputStreamResource
import org.springframework.core.io.Resource
import org.springframework.web.multipart.MultipartFile
import java.awt.image.BufferedImage
import java.io.BufferedInputStream
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileOutputStream
import javax.imageio.ImageIO
import kotlin.jvm.Throws

class FileUtil {

    companion object {

        private val log = logger()

        fun findExtension(fileName: String): String {
            val i = fileName.lastIndexOf('.')
            return if (i > 0)
                fileName.substring(i + 1)
            else
                "jpg"

        }

        suspend fun saveFile(file: File, path: String) {
            withContext(Dispatchers.IO) {
                saveFile(file.readBytes(), path)
            }
        }

        suspend fun saveFile(byteArray: ByteArray, path: String) {
            withContext(Dispatchers.IO) {
                FileOutputStream(path).use {
                    it.write(byteArray)
                }
            }
        }

        suspend fun readImageNonRotate(file: MultipartFile): BufferedImage {
            val byteStream = ByteArrayInputStream(file.bytes)
            val originImage = ImageIO.read(ByteArrayInputStream(file.bytes)) //위에 스트림을 다 읽어놓고 또 읽니
            try {
                val metadata = ImageMetadataReader.readMetadata(byteStream, file.size)
                val directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory::class.java)
                val orientation = directory.getInt(ExifIFD0Directory.TAG_ORIENTATION)
                //1 - 왼쪽으로 눕혀짐, 3 - 오른쪽으로 눕혀짐, 6 - 정위치 8 - 180도
                //왼쪽으로 눕혀지는 경우만 정상적으로 표시되므로 나머지만 rotate
                return rotateImage(originImage, orientation)
            } catch (e: Exception) {
                log.warn("Encountered Exception : {} : {}, return origin image",e::class.simpleName, e.message)
                return originImage
            }
        }

        private suspend fun rotateImage(originImage: BufferedImage, rotate: Int): BufferedImage {
            val image: BufferedImage
            val degree =
                when (rotate) {
                    3 -> 180
                    6 -> 90
                    8 -> 270
                    else -> 0
                }
            if (degree == 90 || degree == 270)
                image = BufferedImage(originImage.height, originImage.width, originImage.type)
            else if (degree == 180)
                image = BufferedImage(originImage.width, originImage.height, originImage.type)
            else
                return originImage //회전할 필요 없음

            val graphic = image.createGraphics()
            graphic.rotate(Math.toRadians(degree.toDouble()), (image.width / 2).toDouble(), (image.height / 2).toDouble())
            graphic.translate((image.width - originImage.width) /2, (image.height - originImage.height) / 2)
            graphic.drawImage(originImage, 0,0, originImage.width, originImage.height, null)
            graphic.dispose()
            return image

        }

        @Throws
        suspend fun convertFileToResource(fileDTO: AbstractFileDTO): Resource {
            val file = File(fileDTO.savePath)
            if (file.exists())
                return InputStreamResource(file.inputStream())
            else
                throw FileException("존재하지 않는 파일입니다.")
        }

        suspend fun deleteFile(path: String) {
            withContext(Dispatchers.IO) {
                val file = File(path)
                if (file.exists())
                    file.delete()
                else
                    log.warn("File ${path} doesn't exist. Skip it")
            }
        }
    }
}