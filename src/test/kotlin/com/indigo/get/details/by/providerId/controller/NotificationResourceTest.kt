package com.indigo.get.details.by.providerId.controller

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Test

@QuarkusTest
class NotificationResourceTest {

    @Test
    fun testHelloEndpoint() {
        given()
          .`when`().get("/notification")
          .then()
             .statusCode(200)
             .body(`is`("Hello from Quarkus REST"))
    }

}