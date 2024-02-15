package com.example.demo.member.user.data

//해당 모델을 업데이트 할 수 있는 인터페이스
// C : ConvertDTO (다른 모델에서 전환할때 - 회원가입..)
// U : UpdateDTO (현재 모델의 데이터를 업데이트 할때)
// R : ResponseDTO (업데이트된 결과를 반환할떄)
interface DataUpdater<C, U, R> {

    suspend fun convert(id : Long, convertDTO : C) : Long?

    suspend fun update(id : Long, updateDTO : U) : R
}