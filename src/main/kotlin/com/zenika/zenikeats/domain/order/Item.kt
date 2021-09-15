package com.zenika.zenikeats.domain.order

import java.math.BigDecimal

data class Item(
    val id: String,
    val name: String,
    val price: BigDecimal,
    val quantity: Int,
)