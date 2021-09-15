package com.zenika.zenikeats.exposition.api.order

data class CreateItemDTO (
    var id: String,
    var name: String,
    var price: Double,
    var quantity: Int,
)
