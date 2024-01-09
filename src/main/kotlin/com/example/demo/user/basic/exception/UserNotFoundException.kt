package com.example.demo.user.basic.exception

class UserNotFoundException(val id : Long, message : String) : Exception(message)