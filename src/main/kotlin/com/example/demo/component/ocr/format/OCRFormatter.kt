package com.example.demo.component.ocr.format

import com.google.cloud.vision.v1.AnnotateImageResponse

interface OCRFormatter {

    fun format(response: AnnotateImageResponse) : String
}