



// package com.indigo.get.details.by.providerId.controller

// import com.indigo.get.details.by.providerId.DTO.NotificationResponse
// import com.indigo.get.details.by.providerId.model.NotificationStats
// import com.indigo.get.details.by.providerId.service.NotificationService
// import io.quarkus.test.junit.QuarkusTest
// import io.quarkus.test.InjectMock
// import io.restassured.RestAssured.given
// import jakarta.ws.rs.core.MediaType
// import org.bson.Document
// import org.hamcrest.Matchers.equalTo
// import org.junit.jupiter.api.Test
// import org.mockito.Mockito.`when`
// import java.math.BigDecimal

// @QuarkusTest
// class NotificationControllerTest {

//     @InjectMock
//     lateinit var mockService: NotificationService

//     @Test
//     fun `should return notification response from endpoint`() {
//         // Given
//         val providerId = "b3aa28d6-1234-4567-9876-aabbccddeeff"
//         val notifications = listOf(
//             Document("channel", "SMS"),
//             Document("channel", "EMAIL")
//         )
//         val stats = listOf(
//             NotificationStats("SMS", 1, BigDecimal("0.50")),
//             NotificationStats("EMAIL", 1, BigDecimal("1.00"))
//         )
//         val expectedResponse = NotificationResponse(notifications, stats)

//         // Mocking
//         `when`(
//             mockService.getNotifications(
//                 providerId, 401, "2024-01-01", "2024-01-31", "SMS"
//             )
//         ).thenReturn(expectedResponse)

//         // When & Then
//         given()
//             .queryParam("applicationId", 401)
//             .queryParam("fromDate", "2024-01-01")
//             .queryParam("toDate", "2024-01-31")
//             .queryParam("channel", "SMS")
//         .`when`()
//             .get("/notification/v1/$providerId/getDetails")
//         .then()
//             .statusCode(200)
//             .contentType(MediaType.APPLICATION_JSON)
//             .body("notifications.size()", equalTo(2))
//             .body("stats.size()", equalTo(2))
//             .body("stats[0].channel", equalTo("SMS"))
//             .body("stats[0].totalCharge", equalTo(0.50f))
//             .body("stats[1].channel", equalTo("EMAIL"))
//             .body("stats[1].totalCharge", equalTo(1.00f))
//     }
// }










// package com.indigo.get.details.by.providerId.controller

// import com.indigo.get.details.by.providerId.DTO.NotificationResponse
// import com.indigo.get.details.by.providerId.model.NotificationStats
// import com.indigo.get.details.by.providerId.service.NotificationService
// import io.quarkus.test.junit.QuarkusTest
// import io.quarkus.test.InjectMock
// import io.restassured.RestAssured.given
// import jakarta.ws.rs.core.MediaType
// import org.bson.Document
// import org.hamcrest.Matchers.equalTo
// import org.junit.jupiter.api.Test
// import org.mockito.Mockito.`when`
// import java.math.BigDecimal

// @QuarkusTest
// class NotificationControllerTest {

//     @InjectMock
//     lateinit var mockService: NotificationService

//     @Test
//     fun `should return notification response from endpoint`() {
//         // Given
//         val providerId = "b3aa28d6-1234-4567-9876-aabbccddeeff"
//         val notifications = listOf(
//             Document("channel", "SMS"),
//             Document("channel", "EMAIL")
//         )
//         val stats = listOf(
//             NotificationStats("SMS", 1, BigDecimal("0.50")),
//             NotificationStats("EMAIL", 1, BigDecimal("1.00"))
//         )
//         val expectedResponse = NotificationResponse(notifications, stats)

//         // Mocking
//         `when`(
//             mockService.getNotifications(
//                 providerId, 401, "2024-01-01", "2024-01-31", "SMS"
//             )
//         ).thenReturn(expectedResponse)

//         // When & Then
//         given()
//             .queryParam("applicationId", 401)
//             .queryParam("fromDate", "2024-01-01")
//             .queryParam("toDate", "2024-01-31")
//             .queryParam("channel", "SMS")
//         .`when`()
//             .get("/notification/v1/$providerId/getDetails")
//         .then()
//             .statusCode(200)
//             .contentType(MediaType.APPLICATION_JSON)
//             .body("notifications.size()", equalTo(2))
//             .body("stats.size()", equalTo(2))
//             .body("stats[0].channel", equalTo("SMS"))
//             .body("stats[0].totalCharge", equalTo(0.50f))
//             .body("stats[1].channel", equalTo("EMAIL"))
//             .body("stats[1].totalCharge", equalTo(1.00f))
//     }
// }
