package com.zenika.zenikeats.exposition.api.order

import com.zenika.zenikeats.application.OrderApplicationService
import com.zenika.zenikeats.domain.order.Item
import com.zenika.zenikeats.domain.order.OrderNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.math.BigDecimal

@RestController
@RequestMapping("/orders")
class OrderController(private val orderService: OrderApplicationService) {

    @PostMapping
    fun postOrder(@RequestBody order: CreateOrderDTO): ResponseEntity<Void> {
        val orderID = orderService.createOrder(
            order.clientID,
            order.items.map { Item(it.id, it.name, BigDecimal(it.price), it.quantity) },
        )

        val location = ServletUriComponentsBuilder.fromCurrentRequestUri()
            .path("/{orderID}")
            .buildAndExpand(orderID)
            .toUri()

        return ResponseEntity.created(location).build()
    }

    @GetMapping("/{orderID}")
    fun getOrder(@PathVariable orderID: String) =
        try {
            orderService.getOrder(orderID).let { OrderDTO(it.id, it.clientID, it.status.name) }
        } catch (e: OrderNotFoundException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
}