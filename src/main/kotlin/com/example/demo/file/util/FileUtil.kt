package com.example.demo.file.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class FileUtil {

    companion object {

        fun findExtension(fileName: String): String {
            val i = fileName.lastIndexOf('.')
            return if (i > 0)
                fileName.substring(i + 1)
            else
                "jpg"

        }

        suspend fun saveFile(file : File, path : String) {
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
    }
}