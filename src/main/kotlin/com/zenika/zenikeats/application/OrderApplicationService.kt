package com.zenika.zenikeats.application

import com.zenika.zenikeats.domain.order.*
import java.time.Clock
import java.time.LocalDateTime

class OrderApplicationService(private val repository: OrderRepository, private val idGenerator: IdGenerator, private val clock: Clock) {
    fun createOrder(clientID: String, items: List<Item>): String {
        val orderID = idGenerator()
        val creationDate = LocalDateTime.now(clock)

        val order = Order(orderID, clientID, items, creationDate)

        repository.save(order)

        return orderID
    }

    fun acceptOrder(orderID: String) {
        val order = repository.findById(orderID) ?: throw OrderNotFoundException(orderID)
        val acceptationDate = LocalDateTime.now(clock)

        order.accept(acceptationDate)

        repository.save(order)
    }

    fun getOrder(orderID: String): Order = repository.findById(orderID) ?: throw OrderNotFoundException(orderID)
}
