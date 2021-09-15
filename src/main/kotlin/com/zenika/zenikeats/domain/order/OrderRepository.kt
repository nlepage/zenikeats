package com.zenika.zenikeats.domain.order

interface OrderRepository {
    fun findById(orderID: String): Order?
    fun save(order: Order)
}
