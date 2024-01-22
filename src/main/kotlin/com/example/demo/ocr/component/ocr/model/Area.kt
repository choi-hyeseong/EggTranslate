package com.example.demo.ocr.component.ocr.model

import com.azure.ai.formrecognizer.documentanalysis.models.Point

data class Area(val min : Point, val max : Point) {

    val height = max.y - min.y

    val width = max.x - min.x
}