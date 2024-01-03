package com.example.demo.component.ocr.google.format

import com.google.cloud.vision.v1.AnnotateImageResponse

interface OCRFormatter {

    fun format(response: AnnotateImageResponse) : String
}