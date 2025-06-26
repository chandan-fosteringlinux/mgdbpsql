

// import jakarta.enterprise.context.ApplicationScoped
// import io.quarkus.hibernate.orm.panache.PanacheRepository



// @ApplicationScoped
// class PartnerChannelRepository : PanacheRepository<PartnerChannel> {
//     fun findByPartnerIdAndChannel(partnerId: String, channel: String): PartnerChannel? {
//         return find("partnerId = ?1 and channel = ?2", partnerId, channel).firstResult()
//     }
// }
// import jakarta.enterprise.context.ApplicationScoped
// import java.sql.Connection
// import javax.sql.DataSource

// @ApplicationScoped
// class ProviderChannelRepository(private val dataSource: DataSource) {

//     fun getChannelsByProviderId(providerId: String): List<String> {
//         val sql = """
//             SELECT channel_type 
//             FROM provider_channels 
//             WHERE provider_id = ?;
//         """.trimIndent()

//         dataSource.connection.use { conn ->
//             conn.prepareStatement(sql).use { stmt ->
//                 stmt.setObject(1, java.util.UUID.fromString(providerId))
//                 stmt.executeQuery().use { rs ->
//                     val channels = mutableListOf<String>()
//                     while (rs.next()) {
//                         channels.add(rs.getString("channel_type"))
//                     }
//                     return channels
//                 }
//             }
//         }
//     }
// }









// import jakarta.enterprise.context.ApplicationScoped
// import java.math.BigDecimal
// import javax.sql.DataSource

// @ApplicationScoped
// class ProviderChannelRepository(private val dataSource: DataSource) {

//     fun getChannelPricing(providerId: String): Map<String, BigDecimal> {
//         val sql = """
//             SELECT channel_type, price_per_message
//             FROM provider_channels 
//             WHERE provider_id = ?;
//         """.trimIndent()

//         val pricingMap = mutableMapOf<String, BigDecimal>()

//         dataSource.connection.use { conn ->
//             conn.prepareStatement(sql).use { stmt ->
//                 stmt.setObject(1, java.util.UUID.fromString(providerId))
//                 stmt.executeQuery().use { rs ->
//                     while (rs.next()) {
//                         val channel = rs.getString("channel_type")
//                         val price = rs.getBigDecimal("price_per_message")
//                         pricingMap[channel] = price
//                     }
//                 }
//             }
//         }
//         return pricingMap
//     }
// }



// import jakarta.enterprise.context.ApplicationScoped
// import io.quarkus.hibernate.orm.panache.PanacheRepository
// import java.util.Optional
// import java.util.UUID

// @ApplicationScoped
// class ProviderChannelRepository : PanacheRepository<ProviderChannel> {

//     /**
//      * Finds the price per message for a given provider and channel type.
//      *
//      * @param providerIdStr The ID of the provider (as String).
//      * @param channel The type of the channel (e.g., "WHATSAPP").
//      * @return The price per message as an Int, or 0 if not found.
//      */
//     fun findPrice(providerIdStr: String, channel: String): Int {
//         val providerId = UUID.fromString(providerIdStr)

//         val optionalResult: Optional<ProviderChannel> = find(
//             "providerId = ?1 and channelType = ?2",
//             providerId,
//             channel
//         ).firstResultOptional()

//         return if (optionalResult.isPresent) {
//             optionalResult.get().pricePerMessage
//         } else {
//             0
//         }
//     }
// }






