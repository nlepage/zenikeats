package com.zenika.zenikeats

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class ZenikeatsApplication

fun main(args: Array<String>) = runApplication<ZenikeatsApplication>(*args)