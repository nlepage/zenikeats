package com.zenika.zenikeats.configuration

import com.zenika.zenikeats.application.OrderApplicationService
import com.zenika.zenikeats.domain.order.IdGenerator
import com.zenika.zenikeats.domain.order.OrderRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Clock

@Configuration
open class ZenikeatsConfiguration {

    @Bean
    open fun clock(): Clock = Clock.systemUTC()

    @Bean
    open fun orderApplicationService(repository: OrderRepository, idGenerator: IdGenerator, clock: Clock) = OrderApplicationService(repository, idGenerator, clock)
}