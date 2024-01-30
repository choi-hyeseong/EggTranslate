package com.example.demo.docs.component

import com.example.demo.docs.dto.DocumentReadResponse
import com.example.demo.docs.dto.DocumentWriteResponse
import java.io.ByteArrayInputStream

//read와 write를 분리해서 reader / writer로 구성하는것이 좋으나, 한 문서 내에서 편집이 지속적으로 이루어져야 하므로 통합.
sealed class DocumentParser(val stream : ByteArrayInputStream) {
    //sealed로 지정하여 구현체 제한. when에 else 안써도됨

    abstract suspend fun read() : DocumentReadResponse

    abstract suspend fun write(translate : String, path : String) : DocumentWriteResponse
}