package com.example.demo

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@SpringBootApplication
@EnableWebMvc
@EnableJpaAuditing
class DemoApplication


fun main(args: Array<String>) {

	runApplication<DemoApplication>(*args).let {  }
}

inline fun <reified T> T.logger() = LoggerFactory.getLogger(T::class.java)!!

// inline을 안시키면 코드가 복사되서 진행되는 방식이 아니라 suspend에서도 동작하지 못함.
//nullable한 객체에서 사용 가능하며, 해당 객체가 null일경우 null리턴, null이 아닐경우 이후에 나오는 블럭의 결과값 리턴.
inline fun <reified Any, R> Any?.convertOrNull(block : (Any) -> R) : R? {
	return if (this == null)
		null
	else
		block(this)
}