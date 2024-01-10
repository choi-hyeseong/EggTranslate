package com.example.demo.signup.validation

import jakarta.validation.Constraint
import kotlin.reflect.KClass
import jakarta.validation.Payload

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [SignUpValidator::class])
annotation class SignUpValid(
        val message: String = "아이디 혹은 비밀번호가 적절하지 않습니다.",
        val groups: Array<KClass<*>> = [],
        val payload: Array<KClass<out Payload>> = [],
)

