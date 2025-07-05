
// package com.indigo.get.details.by.providerId.controller

// import com.indigo.get.details.by.providerId.exception.DatabaseUnavailableException
// import com.indigo.get.details.by.providerId.exception.InvalidProviderIdException
// import com.indigo.get.details.by.providerId.repository.ProviderChannelRepository
// import org.junit.jupiter.api.*
// import org.junit.jupiter.api.Assertions.*
// import org.junit.jupiter.api.extension.ExtendWith
// import org.mockito.Mockito.*
// import org.mockito.junit.jupiter.MockitoExtension
// import java.math.BigDecimal
// import java.sql.*
// import java.util.*
// import javax.sql.DataSource

// @ExtendWith(MockitoExtension::class)
// class ProviderChannelRepositoryTest {

//     private lateinit var dataSource: DataSource
//     private lateinit var connection: Connection
//     private lateinit var statement: PreparedStatement
//     private lateinit var resultSet: ResultSet

//     private lateinit var repository: ProviderChannelRepository

//     @BeforeEach
//     fun setup() {
//         dataSource = mock(DataSource::class.java)
//         connection = mock(Connection::class.java)
//         statement = mock(PreparedStatement::class.java)
//         resultSet = mock(ResultSet::class.java)

//         // Use lenient to suppress UnnecessaryStubbingException
//         lenient().`when`(dataSource.connection).thenReturn(connection)
//         lenient().`when`(connection.prepareStatement(anyString())).thenReturn(statement)
//         lenient().`when`(statement.executeQuery()).thenReturn(resultSet)

//         repository = ProviderChannelRepository(dataSource)
//     }

//     @Test
//     fun `should return channel pricing for valid providerId`() {
//         val providerId = UUID.randomUUID().toString()

//         `when`(resultSet.next()).thenReturn(true, true, false)
//         `when`(resultSet.getString("channel_type")).thenReturn("SMS", "EMAIL")
//         `when`(resultSet.getBigDecimal("price_per_message")).thenReturn(BigDecimal("0.50"), BigDecimal("0.75"))

//         val result = repository.getChannelPricing(providerId)

//         assertEquals(2, result.size)
//         assertEquals(BigDecimal("0.50"), result["SMS"])
//         assertEquals(BigDecimal("0.75"), result["EMAIL"])
//     }

//     @Test
//     fun `should throw InvalidProviderIdException for empty result`() {
//         val providerId = UUID.randomUUID().toString()

//         `when`(resultSet.next()).thenReturn(false)

//         val exception = assertThrows(InvalidProviderIdException::class.java) {
//             repository.getChannelPricing(providerId)
//         }

//         assertEquals("No matching providerId found in PostgreSQL", exception.message)
//     }

//     @Test
//     fun `should throw DatabaseUnavailableException for SQLException`() {
//         val providerId = UUID.randomUUID().toString()

//         // Override connection to throw SQLException specifically for this test
//         `when`(dataSource.connection).thenThrow(SQLException("Connection error"))

//         val exception = assertThrows(DatabaseUnavailableException::class.java) {
//             repository.getChannelPricing(providerId)
//         }

//         assertTrue(exception.message!!.contains("PostgreSQL is not reachable"))
//     }

//     @Test
//     fun `should throw InvalidProviderIdException for invalid UUID`() {
//         val invalidProviderId = "not-a-uuid"

//         val exception = assertThrows(InvalidProviderIdException::class.java) {
//             repository.getChannelPricing(invalidProviderId)
//         }

//         assertTrue(exception.message!!.contains("Invalid UUID format"))
//     }

    
// }

























package com.indigo.get.details.by.providerId.repository

import com.indigo.get.details.by.providerId.exception.DatabaseUnavailableException
import com.indigo.get.details.by.providerId.exception.InvalidProviderIdException
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.math.BigDecimal
import java.sql.*
import java.util.*
import javax.sql.DataSource

@ExtendWith(MockitoExtension::class)
class ProviderChannelRepositoryTest {

    private lateinit var dataSource: DataSource
    private lateinit var connection: Connection
    private lateinit var statement: PreparedStatement
    private lateinit var resultSet: ResultSet

    private lateinit var repository: ProviderChannelRepository

    @BeforeEach
    fun setup() {
        dataSource = mock(DataSource::class.java)
        connection = mock(Connection::class.java)
        statement = mock(PreparedStatement::class.java)
        resultSet = mock(ResultSet::class.java)

        lenient().`when`(dataSource.connection).thenReturn(connection)
        lenient().`when`(connection.prepareStatement(anyString())).thenReturn(statement)
        lenient().`when`(statement.executeQuery()).thenReturn(resultSet)

        repository = ProviderChannelRepository(dataSource)
    }

    @Test
    fun `should return channel pricing for valid providerId`() {
        val providerId = UUID.randomUUID().toString()

        `when`(resultSet.next()).thenReturn(true, true, false)
        `when`(resultSet.getString("channel_type")).thenReturn("SMS", "EMAIL")
        `when`(resultSet.getBigDecimal("price_per_message")).thenReturn(BigDecimal("0.50"), BigDecimal("0.75"))

        val result = repository.getChannelPricing(providerId)

        assertEquals(2, result.size)
        assertEquals(BigDecimal("0.50"), result["SMS"])
        assertEquals(BigDecimal("0.75"), result["EMAIL"])
    }

    @Test
    fun `should throw InvalidProviderIdException for empty result`() {
        val providerId = UUID.randomUUID().toString()

        `when`(resultSet.next()).thenReturn(false)

        val exception = assertThrows(InvalidProviderIdException::class.java) {
            repository.getChannelPricing(providerId)
        }

        assertEquals("No matching providerId found in PostgreSQL", exception.message)
    }

    @Test
    fun `should throw DatabaseUnavailableException when SQLException occurs`() {
        val providerId = UUID.randomUUID().toString()
        val sqlException = SQLException("Connection refused")

        `when`(dataSource.connection).thenThrow(sqlException)

        val exception = assertThrows(DatabaseUnavailableException::class.java) {
            repository.getChannelPricing(providerId)
        }

        assertEquals("Internal Server Error", exception.message)

    }

    @Test
    fun `should throw InvalidProviderIdException when UUID format is invalid`() {
        val invalidProviderId = "bad-uuid-format"

        val exception = assertThrows(InvalidProviderIdException::class.java) {
            repository.getChannelPricing(invalidProviderId)
        }

        assertTrue(exception.message!!.startsWith("Invalid UUID format for providerId:"))
    }
}
