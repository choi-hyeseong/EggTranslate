package com.example.demo.common.valid.lang

import com.example.demo.common.exception.ParameterNotValidException
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.springframework.stereotype.Component
import java.util.*


@Component
class LangValidator : ConstraintValidator<LangValid, String> {

    override fun isValid(p0: String?, p1: ConstraintValidatorContext?): Boolean {
        if (p0.isNullOrEmpty() || !isCountryCode(p0))
            throw ParameterNotValidException("잘못된 언어 코드입니다. ISO Language를 참조하십시오.", p0 ?: "")
        return true
    }

    private fun isCountryCode(s : String) : Boolean {
        return Locale.getISOLanguages().contains(s)
    }


}