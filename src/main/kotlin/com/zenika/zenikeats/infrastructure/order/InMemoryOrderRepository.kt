package com.zenika.zenikeats.infrastructure.order

import com.zenika.zenikeats.domain.order.Order
import com.zenika.zenikeats.domain.order.OrderRepository
import org.springframework.stereotype.Repository

@Repository
class InMemoryOrderRepository : OrderRepository {

    private val orders: MutableMap<String, Order> = HashMap()

    override fun findById(orderID: String) = orders[orderID]

    override fun save(order: Order) {
        orders[order.id] = order
    }
}
