package com.example.demo.admin.model

import com.example.demo.admin.dto.AdminSignUpDTO
import com.example.demo.member.exception.UserException
import org.springframework.boot.context.properties.ConfigurationProperties
import kotlin.jvm.Throws

//admin 자동 생성 데이터 (프로퍼티)
@ConfigurationProperties(prefix = "admin.generate")
data class AdminGenerationData(
    val enable: Boolean, //admin.generate.enable
    val username: String?, //admin.generate.username
    val password: String?, //admin.generate.password
    val email: String?, //admin.generate.email
    val name: String? //admin.generate.name
) {
    @Throws
    fun toAdminDTO(): AdminSignUpDTO {
        if (username == null)
            throw UserException("username이 존재하지 않습니다.")

        if (password == null)
            throw UserException("password가 존재하지 않습니다.")

        if (email == null)
            throw UserException("email이 존재하지 않습니다.")

        if (name == null)
            throw UserException("name이 존재하지 않습니다.")

        return AdminSignUpDTO(username, password, name, email)
    }
}