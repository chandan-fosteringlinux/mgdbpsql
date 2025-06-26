package com.indigo.get.details.by.providerId.service

import jakarta.enterprise.context.ApplicationScoped
import org.bson.Document
import java.math.BigDecimal
import com.indigo.get.details.by.providerId.repository.MongoNotificationRepository
import com.indigo.get.details.by.providerId.repository.ProviderChannelRepository
import com.indigo.get.details.by.providerId.DTO.NotificationResponse
import com.indigo.get.details.by.providerId.model.NotificationStats

@ApplicationScoped
class NotificationService(
    private val mongoRepo: MongoNotificationRepository,
    private val sqlRepo: ProviderChannelRepository
) {
    fun getNotifications(
        providerId: String,
        applicationId: Int?,
        fromDate: String?,
        toDate: String?,
        channel: String?
    ): NotificationResponse {

        val notifications = mongoRepo.findNotifications(applicationId, fromDate, toDate, channel)
        val priceMap = sqlRepo.getChannelPricing(providerId)

        val grouped = notifications.groupBy { it.getString("channel") ?: "UNKNOWN" }

        val stats = grouped.map { (chan, docs) ->
            val count = docs.size
            val price = priceMap[chan.uppercase()] ?: BigDecimal.ZERO
            val total = price.multiply(BigDecimal(count))

            NotificationStats(chan, count, total)
        }

        return NotificationResponse(notifications, stats)
    }
}








































