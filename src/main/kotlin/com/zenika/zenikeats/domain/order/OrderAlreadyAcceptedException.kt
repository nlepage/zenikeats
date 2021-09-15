package com.zenika.zenikeats.domain.order

class OrderAlreadyAcceptedException(orderID: String)
    : Exception("The order \"$orderID\" has already been accepted!")
