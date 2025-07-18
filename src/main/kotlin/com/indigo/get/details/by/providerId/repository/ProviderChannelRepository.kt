
package com.indigo.get.details.by.providerId.repository

import jakarta.enterprise.context.ApplicationScoped
import java.math.BigDecimal
import java.sql.SQLException
import java.util.*
import javax.sql.DataSource
import com.indigo.get.details.by.providerId.exception.InvalidProviderIdException
import com.indigo.get.details.by.providerId.exception.DatabaseUnavailableException

import org.jboss.logging.Logger

@ApplicationScoped
class ProviderChannelRepository(private val dataSource: DataSource) {

    private companion object {
        private val LOG: Logger = Logger.getLogger(ProviderChannelRepository::class.java)
    }

    fun getChannelPricing(providerId: String): Map<String, BigDecimal> {
        val priceMap = mutableMapOf<String, BigDecimal>()
        try {
            LOG.info("Querying PostgreSQL for providerId=$providerId")

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
                    LOG.warn("No matching providerId found: $providerId")
                    throw InvalidProviderIdException("No matching providerId found in PostgreSQL")
                }

                LOG.info("PostgreSQL returned pricing for ${priceMap.size} channels")
            }
        } catch (e: SQLException) {
            LOG.error("PostgreSQL connection failed: ${e.message}", e)
            throw DatabaseUnavailableException("Internal Server Error")
        } catch (e: IllegalArgumentException) {
            LOG.error("Invalid UUID format: ${e.message}", e)
            throw InvalidProviderIdException("Invalid UUID format for providerId: ${e.message}")
        }
        return priceMap
    }
}
