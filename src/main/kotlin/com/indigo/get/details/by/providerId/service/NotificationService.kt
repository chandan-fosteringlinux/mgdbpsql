package com.indigo.get.details.by.providerId.service

import jakarta.enterprise.context.ApplicationScoped
import org.bson.Document
import java.math.BigDecimal
import com.indigo.get.details.by.providerId.repository.MongoNotificationRepository
import com.indigo.get.details.by.providerId.repository.ProviderChannelRepository
import com.indigo.get.details.by.providerId.DTO.NotificationResponse
import com.indigo.get.details.by.providerId.model.NotificationStats
import org.jboss.logging.Logger

@ApplicationScoped
class NotificationService(
    private val mongoRepo: MongoNotificationRepository,
    private val sqlRepo: ProviderChannelRepository
) {

    private companion object {
        private val LOG: Logger = Logger.getLogger(NotificationService::class.java)
    }

    fun getNotifications(
        providerId: String,
        applicationId: Int?,
        fromDate: String?,
        toDate: String?,
        channel: String?
    ): NotificationResponse {
        LOG.info("Fetching notifications from MongoDB...")
        val notifications = mongoRepo.findNotifications(applicationId, fromDate, toDate, channel)
        LOG.info("Fetched ${notifications.size} notifications from MongoDB.")

        LOG.info("Fetching channel pricing from PostgreSQL for providerId=$providerId")
        val priceMap = sqlRepo.getChannelPricing(providerId)
        LOG.info("Fetched pricing for channels: ${priceMap.keys}")

        val grouped = notifications.groupBy { it.getString("channel") ?: "UNKNOWN" }

        val stats = grouped.map { (chan, docs) ->
            val count = docs.size
            val price = priceMap[chan.uppercase()] ?: BigDecimal.ZERO
            val total = price.multiply(BigDecimal(count))
            NotificationStats(chan, count, total)
        }

        LOG.info("Built response statistics: ${stats.map { it.channel to it.count }}")

        return NotificationResponse(notifications, stats)
    }
}
