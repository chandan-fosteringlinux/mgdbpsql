
// import com.mongodb.client.MongoClient
// import jakarta.enterprise.context.ApplicationScoped
// import org.bson.Document
// import java.time.Instant

// @ApplicationScoped
// class NotificationService(private val mongoClient: MongoClient) {
//     private val collection get() = mongoClient
//         .getDatabase("testdb")
//         .getCollection("notifications")

// fun findNotifications(
//     providerId: String,
//     applicationId: Int,
//     fromDate: String,
//     toDate: String,
//     channel: String
// ): List<Document> {

//     val from = Instant.parse("${fromDate}T00:00:00Z")
//     val to = Instant.parse("${toDate}T23:59:59Z")

//     val filter = Document("partnerId", providerId)
//         .append("applicationId", applicationId)
//         .append("channel", channel)
//         .append("timeStamp", Document("\$gte", from).append("\$lte", to))

//     val results = collection.find(filter).toList()

//     val summary = Document()
//         .append("channel", channel)
//         .append("count", results.size)

//     return results + summary
// }

// }







// import com.mongodb.client.MongoClient
// import jakarta.enterprise.context.ApplicationScoped
// import org.bson.Document
// import java.time.Instant

// @ApplicationScoped
// class NotificationService(private val mongoClient: MongoClient) {
//     private val collection get() = mongoClient
//         .getDatabase("testdb")
//         .getCollection("notifications")

//     fun findNotifications(
//         applicationId: Int?,     // nullable
//         fromDate: String?,       // nullable
//         toDate: String?,         // nullable
//         channel: String?         // nullable
//     ): Document {

//         val filter = Document()

//         // Add optional filters only if present
//         applicationId?.let { filter.append("applicationId", it) }

//         if (!fromDate.isNullOrBlank() && !toDate.isNullOrBlank()) {
//             val from = Instant.parse("${fromDate}T00:00:00Z")
//             val to = Instant.parse("${toDate}T23:59:59Z")
//             filter.append("timeStamp", Document("\$gte", from).append("\$lte", to))
//         } else if (!fromDate.isNullOrBlank()) {
//             val from = Instant.parse("${fromDate}T00:00:00Z")
//             filter.append("timeStamp", Document("\$gte", from))
//         } else if (!toDate.isNullOrBlank()) {
//             val to = Instant.parse("${toDate}T23:59:59Z")
//             filter.append("timeStamp", Document("\$lte", to))
//         }

//         channel?.takeIf { it.isNotBlank() }?.let {
//             filter.append("channel", it)
//         }

//         val results = collection.find(filter).toList()

//         // Group and count by channel
//         val grouped = results.groupBy { it.getString("channel") ?: "UNKNOWN" }
//         val summary = grouped.map { (chan, docs) ->
//             Document("channel", chan).append("count", docs.size)
//         }

//         return Document()
//             .append("notifications", results)
//             .append("summary", summary)
//     }
// }


import com.mongodb.client.MongoClient
import jakarta.enterprise.context.ApplicationScoped
import org.bson.Document
import java.math.BigDecimal
import java.time.Instant

@ApplicationScoped
class NotificationService(
    private val mongoClient: MongoClient,
) {
    private val collection get() = mongoClient
        .getDatabase("testdb")
        .getCollection("notifications")

    fun findNotifications(
        providerId: String,
        applicationId: Int?,
        fromDate: String?,
        toDate: String?,
        channel: String?
    ): Document {
        val filter = Document()

        applicationId?.let {
            filter.append("applicationId", it)
        }

        if (!fromDate.isNullOrBlank() && !toDate.isNullOrBlank()) {
            val from = Instant.parse("${fromDate}T00:00:00Z")
            val to = Instant.parse("${toDate}T23:59:59Z")
            filter.append("timeStamp", Document("\$gte", from).append("\$lte", to))
        } else if (!fromDate.isNullOrBlank()) {
            val from = Instant.parse("${fromDate}T00:00:00Z")
            filter.append("timeStamp", Document("\$gte", from))
        } else if (!toDate.isNullOrBlank()) {
            val to = Instant.parse("${toDate}T23:59:59Z")
            filter.append("timeStamp", Document("\$lte", to))
        }

        // Get pricing from PostgreSQL

        if (!channel.isNullOrBlank()) {
            filter.append("channel", channel)
        } 
        val results = collection.find(filter).toList()

        // Group and count by channel
        val grouped = results.groupBy { it.getString("channel") ?: "UNKNOWN" }
        val summary = grouped.map { (chan, docs) ->
            Document("channel", chan).append("count", docs.size)
        }

        return Document()
            .append("notifications", results)
            .append("summary", summary)
    }
}


// import com.mongodb.client.MongoClient
// import jakarta.enterprise.context.ApplicationScoped
// import org.bson.Document
// import java.time.Instant

// @ApplicationScoped
// class NotificationService(
//     private val mongoClient: MongoClient,
//     private val providerRepo: ProviderChannelRepository
// ) {
//     private val collection get() = mongoClient
//         .getDatabase("testdb")
//         .getCollection("notifications")

//     fun findNotifications(
//         providerId: String,
//         applicationId: Int,
//         fromDate: String?,
//         toDate: String?,
//         channel: String
//     ): Map<String, Any> {

//         // Build Mongo filter
//         val filter = Document("channel", channel)
//             .append("applicationId", applicationId)
//             .append("partnerId", providerId)

//         if (!fromDate.isNullOrBlank() && !toDate.isNullOrBlank()) {
//             val from = Instant.parse("${fromDate}T00:00:00Z")
//             val to = Instant.parse("${toDate}T23:59:59Z")
//             filter.append("timeStamp", Document("\$gte", from).append("\$lte", to))
//         }

//         val results = collection.find(filter).toList()

//         // Fetch price from PostgreSQL
//         val pricePerMessage = providerRepo.findPrice(providerId, channel)

//         // Calculate charge
//         val charge = results.size * pricePerMessage

//         val summary = mapOf(
//             "channel" to channel,
//             "charge" to charge
//         )

//         return mapOf(
//             "notifications" to results,
//             "summary" to listOf(summary)
//         )
//     }
// }



// import com.mongodb.client.MongoClient
// import jakarta.enterprise.context.ApplicationScoped
// import org.bson.Document
// import java.time.Instant

// @ApplicationScoped
// class NotificationService(
//     private val mongoClient: MongoClient,
//     private val providerRepo: ProviderChannelRepository
// ) {
//     private val collection get() = mongoClient
//         .getDatabase("testdb")
//         .getCollection("notifications")

//     fun findNotifications(
//         providerId: String,
//         applicationId: Int?,
//         fromDate: String?,
//         toDate: String?,
//         channel: String?
//     ): Map<String, Any> {
//         val filter = Document()

//         applicationId?.let { filter.append("applicationId", it) }
//         channel?.let { filter.append("channel", it) }
//         filter.append("partnerId", providerId)

//         if (!fromDate.isNullOrBlank() && !toDate.isNullOrBlank()) {
//             val from = Instant.parse("${fromDate}T00:00:00Z")
//             val to = Instant.parse("${toDate}T23:59:59Z")
//             filter.append("timestamp", Document("\$gte", from).append("\$lte", to))
//         }

//         val results = collection.find(filter).toList()

//         val pricePerMessage = providerRepo.findPrice(providerId, channel ?: "")
//         val charge = results.size * pricePerMessage

//         val summary = mapOf(
//             "channel" to (channel ?: "UNKNOWN"),
//             "charge" to charge
//         )

//         return mapOf(
//             "notifications" to results,
//             "summary" to listOf(summary)
//         )
//     }
// }
