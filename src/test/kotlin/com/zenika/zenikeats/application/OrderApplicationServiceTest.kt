package com.zenika.zenikeats.application

import com.zenika.zenikeats.domain.order.*
import com.zenika.zenikeats.infrastructure.order.InMemoryOrderRepository
import org.junit.jupiter.api.assertThrows
import java.time.Clock
import java.time.LocalDate
import java.time.ZoneOffset
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

internal class OrderApplicationServiceTest {

    private val date = LocalDate.of(2021, 1, 1).atStartOfDay()
    private val clock = Clock.fixed(date.toInstant(ZoneOffset.UTC), ZoneOffset.UTC)

    @Test
    fun `creates an order`() {
        val repository: OrderRepository = InMemoryOrderRepository()
        val service = OrderApplicationService(repository, { "orderID" }, clock)

        val orderID = service.createOrder("clientID", emptyList<Item>())
        val nullableOrder = repository.findById(orderID)

        val order = assertNotNull(nullableOrder)
        assertEquals(order.creationDate, date)
    }

    @Test
    fun `accepts an order`() {
        val repository: OrderRepository = InMemoryOrderRepository()
        val service = OrderApplicationService(repository, { "orderID" }, clock)
        repository.save(Order("orderID", "clientID", emptyList(), date))

        service.acceptOrder("orderID")

        val order = assertNotNull(repository.findById("orderID"))
        assertEquals(OrderStatus.ACCEPTED, order.status)
    }

    @Test
    fun `gets an order`() {
        val repository: OrderRepository = InMemoryOrderRepository()
        val service = OrderApplicationService(repository, { "orderID" }, clock)
        repository.save(Order("orderID", "clientID", emptyList(), date))

        val order = service.getOrder("orderID")

        assertEquals("orderID", order.id)
    }

    @Test
    fun `gets an invalid order`() {
        val repository: OrderRepository = InMemoryOrderRepository()
        val service = OrderApplicationService(repository, { "orderID" }, clock)
        repository.save(Order("orderID", "clientID", emptyList(), date))

        assertThrows<OrderNotFoundException>("The order \"unknownOrderID\" does not exist") {
            service.getOrder("unknownOrderID")
        }
    }
}