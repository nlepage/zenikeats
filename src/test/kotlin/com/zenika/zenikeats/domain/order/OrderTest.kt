package com.zenika.zenikeats.domain.order

import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal
import java.time.LocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals

internal class OrderTest {

    @Test
    fun `an order has an ID`() {
        val order = Order("orderID", "clientID", emptyList(), LocalDateTime.now())
        assertEquals("orderID", order.id)
    }

    @Test
    fun `an order has a list of items`() {
        val items = listOf(Item("itemID", "itemName", BigDecimal.TEN, 2))
        val order = Order("orderID", "clientID", items, LocalDateTime.now())
        assertAll("Items",
            { assertEquals(1, order.items.size) },
            { assertEquals("itemID", order.items[0].id) },
            { assertEquals("itemName", order.items[0].name) },
            { assertEquals(BigDecimal.TEN, order.items[0].price) },
            { assertEquals(2, order.items[0].quantity) },
        )
    }

    @Test
    fun `an order belongs to a client`() {
        val order = Order("orderID", "clientID", emptyList(), LocalDateTime.now())
        assertEquals("clientID", order.clientID)
    }

    @Test
    fun `a new order has status created`() {
        val order = Order("orderID", "clientID", emptyList(), LocalDateTime.now())
        assertEquals(OrderStatus.CREATED, order.status)
    }

    @Test
    fun `a new order has a status history`() {
        val creationDate = LocalDateTime.now()
        val order = Order("orderID", "clientID", emptyList(), creationDate)

        assertAll("Status history",
            { assertEquals(1, order.statusHistories.size) },
            { assertEquals(OrderStatus.CREATED, order.statusHistories[0].status) },
            { assertEquals(creationDate, order.statusHistories[0].date) },
        )
    }

    @Test
    fun `status and status history are updated when order is accepted`() {
        val order = Order("orderID", "clientID", emptyList(), LocalDateTime.now())
        val acceptationDate = LocalDateTime.now()
        order.accept(acceptationDate)

        assertAll("Status history",
            { assertEquals(2, order.statusHistories.size) },
            { assertEquals(OrderStatus.ACCEPTED, order.statusHistories[1].status) },
            { assertEquals(acceptationDate, order.statusHistories[1].date) },
        )
    }

    @Test
    fun `driver ID is set when driver has accepted delivery`() {
        val order = Order("orderID", "clientID", emptyList(), LocalDateTime.now())
        order.accept(LocalDateTime.now())

        order.acceptDelivery("driverID")

        assertEquals("driverID", order.driverID)
    }

    @Test
    fun `driver cannot accept an order if the restaurant hasn't accepted it`() {
        val order = Order("orderID", "clientID", emptyList(), LocalDateTime.now())

        assertThrows<OrderNotAcceptedException>("The order \"orderID\" has not been accepted yet!") {
            order.acceptDelivery("driverID")
        }
    }

    @Test
    fun `driver cannot accept twice the same order`() {
        val order = Order("orderID", "clientID", emptyList(), LocalDateTime.now())
        order.accept(LocalDateTime.now())

        order.acceptDelivery("driverID")

        assertThrows<OrderAlreadyAcceptedException>("The order \"orderID\" has already been accepted!") {
            order.acceptDelivery("driverID")
        }
    }
}