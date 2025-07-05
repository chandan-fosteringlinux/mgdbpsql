// import com.indigo.get.details.by.providerId.DTO.NotificationResponse
// import com.indigo.get.details.by.providerId.controller.NotificationController
// import com.indigo.get.details.by.providerId.model.NotificationStats
// import com.indigo.get.details.by.providerId.service.NotificationService
// import org.bson.Document
// import org.junit.jupiter.api.Assertions.assertEquals
// import org.junit.jupiter.api.BeforeEach
// import org.junit.jupiter.api.Test
// import org.mockito.Mockito.*
// import java.math.BigDecimal

// class NotificationControllerTest {

//     private lateinit var service: NotificationService
//     private lateinit var controller: NotificationController

//     @BeforeEach
//     fun setup() {
//         service = mock(NotificationService::class.java)
//         controller = NotificationController(service)
//     }

//     @Test
//     fun `should return NotificationResponse from service`() {
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

//         `when`(
//             service.getNotifications(
//                 providerId,
//                 401,
//                 "2024-01-01",
//                 "2024-01-31",
//                 "SMS"
//             )
//         ).thenReturn(expectedResponse)

//         val actual = controller.getDetails(
//             providerId,
//             401,
//             "2024-01-01",
//             "2024-01-31",
//             "SMS"
//         )

//         assertEquals(expectedResponse, actual)
//         verify(service, times(1)).getNotifications(providerId, 401, "2024-01-01", "2024-01-31", "SMS")
//     }
// }

import com.indigo.get.details.by.providerId.DTO.NotificationResponse
import com.indigo.get.details.by.providerId.controller.NotificationController
import com.indigo.get.details.by.providerId.exception.DatabaseUnavailableException
import com.indigo.get.details.by.providerId.exception.InvalidProviderIdException
import com.indigo.get.details.by.providerId.model.NotificationStats
import com.indigo.get.details.by.providerId.service.NotificationService
import org.bson.Document
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito.*
import java.math.BigDecimal

class NotificationControllerTest {

    private lateinit var service: NotificationService
    private lateinit var controller: NotificationController

    @BeforeEach
    fun setup() {
        service = mock(NotificationService::class.java)
        controller = NotificationController(service)
    }

    @Test
    fun `should return NotificationResponse from service`() {
        val providerId = "b3aa28d6-1234-4567-9876-aabbccddeeff"
        val notifications = listOf(
            Document("channel", "SMS"),
            Document("channel", "EMAIL")
        )
        val stats = listOf(
            NotificationStats("SMS", 1, BigDecimal("0.50")),
            NotificationStats("EMAIL", 1, BigDecimal("1.00"))
        )
        val expectedResponse = NotificationResponse(notifications, stats)

        `when`(
            service.getNotifications(
                providerId,
                401,
                "2024-01-01",
                "2024-01-31",
                "SMS"
            )
        ).thenReturn(expectedResponse)

        val actual = controller.getDetails(
            providerId,
            401,
            "2024-01-01",
            "2024-01-31",
            "SMS"
        )

        assertEquals(expectedResponse, actual)
        verify(service, times(1)).getNotifications(providerId, 401, "2024-01-01", "2024-01-31", "SMS")
    }

    @Test
    fun `should throw InvalidProviderIdException when service throws it`() {
        val providerId = "invalid-id"
        `when`(service.getNotifications(providerId, null, null, null, null))
            .thenThrow(InvalidProviderIdException("Invalid UUID format"))

        val exception = assertThrows(InvalidProviderIdException::class.java) {
            controller.getDetails(providerId, null, null, null, null)
        }

        assertEquals("Invalid UUID format", exception.message)
    }

    @Test
    fun `should throw DatabaseUnavailableException when service throws it`() {
        val providerId = "b3aa28d6-1234-4567-9876-aabbccddeeff"
        `when`(service.getNotifications(providerId, null, null, null, null))
            .thenThrow(DatabaseUnavailableException("Internal Server Error"))

        val exception = assertThrows(DatabaseUnavailableException::class.java) {
            controller.getDetails(providerId, null, null, null, null)
        }

        assertEquals("Internal Server Error", exception.message)
    }
}
