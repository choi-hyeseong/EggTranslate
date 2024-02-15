package com.example.demo.member.translator.converter

import com.example.demo.common.database.converter.AbstractStringFlatter
import com.example.demo.member.translator.type.TranslatorCategory

class TranslatorCategoryFlatter : AbstractStringFlatter<TranslatorCategory>() {
    override fun recover(s: String): TranslatorCategory {
        return TranslatorCategory.fromString(s)
    }

    override fun convert(obj: TranslatorCategory): String {
        return obj.toString()
    }
}