package com.example.demo.auth.jwt.model


//jwt에 들어갈 정보
data class UserClaim(val userName : String, val id : Long, val email : String)