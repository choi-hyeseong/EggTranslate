package com.example.demo

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@SpringBootApplication
@EnableWebMvc
class DemoApplication



fun main(args: Array<String>) {

	runApplication<DemoApplication>(*args)
}

inline fun <reified T> T.logger() = LoggerFactory.getLogger(T::class.java)!!