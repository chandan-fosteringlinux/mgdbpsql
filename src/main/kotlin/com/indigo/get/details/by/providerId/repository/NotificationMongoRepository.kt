

package com.indigo.get.details.by.providerId.repository

import com.mongodb.client.MongoClient
import com.indigo.get.details.by.providerId.exception.DatabaseUnavailableException
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.bson.Document
import java.time.Instant
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.jboss.logging.Logger


@ApplicationScoped
class MongoNotificationRepository @Inject constructor(
    private val mongoClient: MongoClient,
    @ConfigProperty(name = "quarkus.mongodb.database") private val databaseName: String,
    @ConfigProperty(name = "quarkus.mongodb.collection") private val collectionName: String
) {

    private companion object {
        private val LOG: Logger = Logger.getLogger(MongoNotificationRepository::class.java)
    }

    private val collection get() = mongoClient
        .getDatabase(databaseName)
        .getCollection(collectionName)

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

        LOG.info("MongoDB query filter: $filter")

        return try {
            val results = collection.find(filter).toList()
            LOG.info("MongoDB returned ${results.size} records.")
            results
        } catch (e: Exception) {
            LOG.error("MongoDB query failed: ${e.message}", e)
            throw DatabaseUnavailableException("Internal Server Error")
        }
    }
}
