package com.example.demo.docs.valid

import com.example.demo.common.exception.ParameterNotValidException
import com.example.demo.common.valid.lang.LangValidator
import com.example.demo.docs.component.DocumentResolver
import com.example.demo.docs.dto.DocumentRequestDTO
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import kotlinx.coroutines.runBlocking
import kotlin.jvm.Throws

class DocumentRequestValidator (
    private val langValidator: LangValidator,
    private val documentResolver: DocumentResolver
    ) : ConstraintValidator<DocumentRequestValid, DocumentRequestDTO> {
        //https://hogwart-scholars.tistory.com/entry/Spring-Boot-ConstraintValidator%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%B4-%EB%82%98%EB%A7%8C%EC%9D%98-validator-%EB%A7%8C%EB%93%A4%EA%B8%B0
        //이걸 원래 여기서 throw 하는게 맞는지 헷갈리긴함. MethodValidException 던져줘서 그거 핸들링 했었는데 최근에는 달라진듯
        @Throws
        override fun isValid(p0: DocumentRequestDTO?, p1: ConstraintValidatorContext?): Boolean {
            if (p0 == null || p0.file.isEmpty())
                throw ParameterNotValidException("파일은 빈 값이 올 수 없습니다.", null)

            //만약 지원하지 않는 파일이 있을경우 throw 발생
            runBlocking {
                p0.file.forEach { documentResolver.resolve(it) }
            }

            return langValidator.isValid(p0.lang, p1)
        }



}
