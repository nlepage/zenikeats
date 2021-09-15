package com.zenika.zenikeats.exposition.api.order

data class CreateOrderDTO (
    var clientID: String,
    var items: Array<CreateItemDTO>,
)
