package com.example.demo.docs.component.pdf

import java.io.ByteArrayInputStream

interface PDFConverter {
    fun convertPdfToDocx(stream : ByteArrayInputStream) : ByteArrayInputStream
}