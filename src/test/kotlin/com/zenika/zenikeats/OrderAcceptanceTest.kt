package com.zenika.zenikeats

import com.lectra.koson.arr
import com.lectra.koson.obj
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import kotlin.test.Test

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
internal class OrderAcceptanceTest(
    @Autowired val mockMvc: MockMvc
) {

    @Test
    fun `an order is created after shopping cart has been validated`() {
        val orderPayload = obj {
            "clientID" to "clientID"
            "items" to arr [
                obj {
                    "id" to "itemID"
                    "name" to "itemName"
                    "price" to 20
                    "quantity" to 1
                }
            ]
        }.toString()

        val result = mockMvc.perform(post("/orders")
                .content(orderPayload)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isCreated)
                .andExpect(header().exists("Location"))
                .andReturn()

        val orderLocation = result.response.getHeader("Location")

        mockMvc.perform(get(orderLocation))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk)
            // .andExpect(jsonPath("$.orderId").value(orderId))
            .andExpect(jsonPath("$.clientID").value("clientID"))
            .andExpect(jsonPath("$.status").value("CREATED"))
    }
}