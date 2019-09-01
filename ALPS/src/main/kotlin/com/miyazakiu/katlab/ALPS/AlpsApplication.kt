package com.miyazakiu.katlab.ALPS

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
//@Controller
class AlpsApplication

@RequestMapping("/")
fun hello(): String{
	return "hello"
}

fun main(args: Array<String>) {
	runApplication<AlpsApplication>(*args)
}
