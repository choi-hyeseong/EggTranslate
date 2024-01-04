package com.example.demo.image.validation

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass


@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ImageValidator::class])
annotation class ImageValid(
        val message: String = "잘못된 이미지 파일입니다.",
        val groups: Array<KClass<*>> = [],
        val payload: Array<KClass<out Payload>> = [],
)