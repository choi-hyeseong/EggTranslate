package com.example.demo.auth.security.encoder

import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class EncoderBean {

    //security에 지정했더니 순환참조 발생해서.. 따로
    @Bean
    fun passwordEncoder() : PasswordEncoder {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }
}