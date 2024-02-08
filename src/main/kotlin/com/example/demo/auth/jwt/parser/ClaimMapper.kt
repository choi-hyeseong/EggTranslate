package com.example.demo.auth.jwt.parser

import com.example.demo.auth.jwt.model.UserClaim
import com.example.demo.logger
import io.jsonwebtoken.Claims
import org.springframework.stereotype.Component

@Component
class ClaimMapper {

    val log = logger()
    //왜 분리했는가? - 추후에 userClaim에 더 많은 내용이 들어갈경우 수정할 소요가 생길 가능성이 있음.
    //따라서 Token Provider를 수정하는것이 아닌 얘를 수정하는게 맞음
    fun createClaim(userClaim: UserClaim): Map<String, Any?> {
        //더 넣을 수 있는 값들
        //setSubject로 userName을 넣을 수 있으나, 이왕 userClaim으로 받은김에 직접 넣기
        return mapOf("sub" to userClaim.userName, "id" to userClaim.id, "email" to userClaim.email)
    }

    //파싱 실패시 null
    fun decodeClaim(claims: Claims) : UserClaim? {
        return try {
            UserClaim(
                claims.subject,
                claims.get("id", Integer::class.java).toLong(), //gson 버그 때문에 Long을 제대로 파싱 못함. 일단 Int로 받음
                claims.get("email", String::class.java)
            )
        } catch (e : Exception) {
            log.warn("Can't decode claim : ${e.localizedMessage}")
            null
        }
    }
}