
package com.indigo.get.details.by.providerId.repository

import jakarta.enterprise.context.ApplicationScoped
import java.math.BigDecimal
import java.sql.SQLException
import java.util.*
import javax.sql.DataSource
import com.indigo.get.details.by.providerId.exception.InvalidProviderIdException
import com.indigo.get.details.by.providerId.exception.DatabaseUnavailableException

@ApplicationScoped
class ProviderChannelRepository(private val dataSource: DataSource) {

fun getChannelPricing(providerId: String): Map<String, BigDecimal> {
    val priceMap = mutableMapOf<String, BigDecimal>()
    try {
        dataSource.connection.use { connection ->
            val statement = connection.prepareStatement(
                "SELECT channel_type, price_per_message FROM provider_channels WHERE provider_id = ?"
            )
            statement.setObject(1, UUID.fromString(providerId))
            val resultSet = statement.executeQuery()

            var found = false
            while (resultSet.next()) {
                found = true
                val channelType = resultSet.getString("channel_type")
                val price = resultSet.getBigDecimal("price_per_message")
                priceMap[channelType.uppercase()] = price
            }

            if (!found) {
                throw InvalidProviderIdException("No matching providerId found in PostgreSQL")
            }
        }
    } catch (e: SQLException) {
        throw DatabaseUnavailableException("PostgreSQL is not reachable: ${e.message}")
    } catch (e: IllegalArgumentException) {
        throw InvalidProviderIdException("Invalid UUID format for providerId: ${e.message}")
    }

    return priceMap
}
}