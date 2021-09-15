package com.zenika.zenikeats.domain.order

class OrderNotAcceptedException(orderID: String)
    : Exception("The order \"$orderID\" has not been accepted yet!")