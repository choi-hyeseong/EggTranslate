package com.example.demo.signup.validation

import jakarta.validation.Constraint
import kotlin.reflect.KClass
import jakarta.validation.Payload

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [UsernameValidator::class])
annotation class UsernameValid(
        val message: String = "중복된 아이디가 있습니다.",
        val groups: Array<KClass<*>> = [],
        val payload: Array<KClass<out Payload>> = [],
)

