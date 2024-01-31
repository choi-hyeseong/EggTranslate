package com.example.demo.docs.valid

import com.example.demo.image.validation.ImageValidator
import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [DocumentRequestValidator::class])
annotation class DocumentRequestValid(
    val message: String = "처리할 수 없는 문서 파일입니다.",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
)