package com.zenika.zenikeats.domain.order

import java.time.LocalDateTime

data class StatusHistory(val status: OrderStatus, val date: LocalDateTime)
