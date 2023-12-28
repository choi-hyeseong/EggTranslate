package com.example.demo.common.response

class ObjectResponse<T>(val isSuccess : Boolean, val response : T) : Response(isSuccess)