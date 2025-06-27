import com.indigo.get.details.by.providerId.DTO.NotificationResponse
import com.indigo.get.details.by.providerId.model.NotificationStats
import com.indigo.get.details.by.providerId.repository.MongoNotificationRepository
import com.indigo.get.details.by.providerId.repository.ProviderChannelRepository
import com.indigo.get.details.by.providerId.service.NotificationService
import org.bson.Document
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.math.BigDecimal

class NotificationServiceTest {

    private lateinit var mongoRepo: MongoNotificationRepository
    private lateinit var sqlRepo: ProviderChannelRepository
    private lateinit var service: NotificationService

    @BeforeEach
    fun setup() {
        mongoRepo = mock(MongoNotificationRepository::class.java)
        sqlRepo = mock(ProviderChannelRepository::class.java)
        service = NotificationService(mongoRepo, sqlRepo)
    }

    @Test
    fun `should return correct NotificationResponse with grouped stats`() {
        val providerId = "b3aa28d6-1234-4567-9876-aabbccddeeff"
        val notifications = listOf(
            Document("channel", "SMS"),
            Document("channel", "SMS"),
            Document("channel", "EMAIL")
        )

        val priceMap = mapOf(
            "SMS" to BigDecimal("0.50"),
            "EMAIL" to BigDecimal("1.00")
        )

        `when`(mongoRepo.findNotifications(401, "2024-01-01", "2024-01-31", "SMS")).thenReturn(notifications)
        `when`(sqlRepo.getChannelPricing(providerId)).thenReturn(priceMap)

        val response: NotificationResponse = service.getNotifications(
            providerId,
            401,
            "2024-01-01",
            "2024-01-31",
            "SMS"
        )

        assertEquals(3, response.notifications.size)
        assertEquals(2, response.stats.size)

        val smsStat = response.stats.first { it.channel == "SMS" }
        assertEquals(2, smsStat.count)
        assertEquals(BigDecimal("1.00"), smsStat.totalCharge)

        val emailStat = response.stats.first { it.channel == "EMAIL" }
        assertEquals(1, emailStat.count)
        assertEquals(BigDecimal("1.00"), emailStat.totalCharge)
    }
}
