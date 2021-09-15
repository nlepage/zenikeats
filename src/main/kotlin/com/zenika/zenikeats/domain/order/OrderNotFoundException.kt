package com.zenika.zenikeats.domain.order

class OrderNotFoundException(orderID: String)
    : Exception("The order \"$orderID\" does not exist")