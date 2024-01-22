package com.example.demo.file.util

import com.example.demo.file.dto.AbstractFileDTO
import com.example.demo.file.dto.FileDTO
import com.example.demo.file.exception.FileException
import com.example.demo.logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.core.io.InputStreamResource
import org.springframework.core.io.Resource
import java.io.File
import java.io.FileOutputStream
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

        @Throws
        suspend fun convertFileToResource(fileDTO: AbstractFileDTO): Resource {
            val file = File(fileDTO.savePath)
            if (file.exists())
                return InputStreamResource(file.inputStream())
            else
                throw FileException("존재하지 않는 파일입니다.")
        }

        suspend fun deleteFile(path : String) {
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