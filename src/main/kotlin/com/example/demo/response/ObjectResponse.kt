package com.example.demo.response

class ObjectResponse<T>(val isSuccess : Boolean, val response : T) : Response(isSuccess)