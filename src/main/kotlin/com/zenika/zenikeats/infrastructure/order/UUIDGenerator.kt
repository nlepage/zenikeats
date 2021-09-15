package com.zenika.zenikeats.infrastructure.order

import com.zenika.zenikeats.domain.IdGenerator
import org.springframework.stereotype.Component
import java.util.*

@Component
class UUIDGenerator : IdGenerator {
    override fun invoke() = UUID.randomUUID().toString()
}