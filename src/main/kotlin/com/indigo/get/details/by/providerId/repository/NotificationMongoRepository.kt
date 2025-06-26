package com.indigo.get.details.by.providerId.repository

import com.mongodb.client.MongoClient
import com.indigo.get.details.by.providerId.exception.DatabaseUnavailableException
import jakarta.enterprise.context.ApplicationScoped
import org.bson.Document
import java.time.Instant

@ApplicationScoped
class MongoNotificationRepository(private val mongoClient: MongoClient) {

    private val collection get() = mongoClient
        .getDatabase("testdb")
        .getCollection("notifications")


fun findNotifications(
    applicationId: Int?,
    fromDate: String?,
    toDate: String?,
    channel: String?
): List<Document> {
    val filter = Document()

    applicationId?.let { filter.append("applicationId", it) }

    if (!fromDate.isNullOrBlank()) {
        val from = Instant.parse("${fromDate}T00:00:00Z")
        filter.append("timeStamp", Document("\$gte", from))
    }
    if (!toDate.isNullOrBlank()) {
        val to = Instant.parse("${toDate}T23:59:59Z")
        val existing = filter["timeStamp"] as? Document ?: Document()
        existing.append("\$lte", to)
        filter["timeStamp"] = existing
    }

    if (!channel.isNullOrBlank()) {
        filter.append("channel", channel)
    }

    try {
        return collection.find(filter).toList()
    } catch (e: Exception) {
        throw DatabaseUnavailableException("MongoDB is not reachable: ${e.message}")
    }
}
}
