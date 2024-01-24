package com.example.demo.user.translator.converter

import com.example.demo.common.database.converter.AbstractStringFlatter
import com.example.demo.user.translator.type.TranslatorCategory

class TranslatorCategoryFlatter : AbstractStringFlatter<TranslatorCategory>() {
    override fun recover(s: String): TranslatorCategory {
        return TranslatorCategory.fromString(s)
    }

    override fun convert(obj: TranslatorCategory): String {
        return obj.toString()
    }
}