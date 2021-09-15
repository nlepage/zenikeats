package com.zenika.zenikeats.domain.order

import java.time.LocalDateTime

class Order(id: String, clientID: String, items: List<Item>, creationDate: LocalDateTime) {
    val id = id

    val clientID = clientID

    val items: List<Item> = items.toList()

    var status = OrderStatus.CREATED
        private set

    val creationDate = creationDate


    val statusHistories: List<StatusHistory>
        get() = _statusHistories.toList()
    private var _statusHistories = MutableList(1) { StatusHistory(OrderStatus.CREATED, creationDate) }

    var driverID: String? = null
        private set

    fun accept(date: LocalDateTime) {
        status = OrderStatus.ACCEPTED
        _statusHistories.add(StatusHistory(OrderStatus.ACCEPTED, date))
    }

    fun acceptDelivery(driverID: String) {
        if (status != OrderStatus.ACCEPTED) throw OrderNotAcceptedException(id)
        if (this.driverID != null) throw OrderAlreadyAcceptedException(id)
        this.driverID = driverID
    }
}