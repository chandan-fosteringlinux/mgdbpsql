// package com.indigo.get.details.by.providerId.repository

// import com.indigo.get.details.by.providerId.exception.DatabaseUnavailableException
// import com.mongodb.client.FindIterable
// import com.mongodb.client.MongoClient
// import com.mongodb.client.MongoCollection
// import com.mongodb.client.MongoDatabase
// import org.bson.Document
// import org.junit.jupiter.api.*
// import org.junit.jupiter.api.Assertions.*
// import org.junit.jupiter.api.extension.ExtendWith
// import org.mockito.Mockito.*
// import org.mockito.junit.jupiter.MockitoExtension

// @ExtendWith(MockitoExtension::class)
// class MongoNotificationRepositoryTest {

//     private lateinit var mongoClient: MongoClient
//     private lateinit var mongoDatabase: MongoDatabase
//     private lateinit var mongoCollection: MongoCollection<Document>
//     private lateinit var repository: MongoNotificationRepository

//     @BeforeEach
//     fun setUpBaseMocks() {
//         mongoClient = mock(MongoClient::class.java)
//         mongoDatabase = mock(MongoDatabase::class.java)
//         @Suppress("UNCHECKED_CAST")
//         mongoCollection = mock(MongoCollection::class.java) as MongoCollection<Document>

//         `when`(mongoClient.getDatabase("testdb")).thenReturn(mongoDatabase)
//         `when`(mongoDatabase.getCollection("notifications")).thenReturn(mongoCollection)

//         repository = MongoNotificationRepository(
//             mongoClient = mongoClient,
//             databaseName = "testdb",
//             collectionName = "notifications"
//         )
//     }

//     @Test
//     fun `should return filtered notifications`() {
//         @Suppress("UNCHECKED_CAST")
//         val findIterable = mock(FindIterable::class.java) as FindIterable<Document>
//         val mongoCursor = mock(com.mongodb.client.MongoCursor::class.java) as com.mongodb.client.MongoCursor<Document>

//         val documents = listOf(
//             Document("applicationId", 101).append("channel", "EMAIL"),
//             Document("applicationId", 102).append("channel", "SMS")
//         )

//         val iterator = documents.iterator()
//         `when`(mongoCursor.hasNext()).thenAnswer { iterator.hasNext() }
//         `when`(mongoCursor.next()).thenAnswer { iterator.next() }
//         `when`(findIterable.iterator()).thenReturn(mongoCursor)
//         `when`(mongoCollection.find(any(Document::class.java))).thenReturn(findIterable)

//         val result = repository.findNotifications(
//             applicationId = 101,
//             fromDate = "2024-01-01",
//             toDate = "2024-01-31",
//             channel = "EMAIL"
//         )

//         assertEquals(2, result.size)
//         assertEquals(101, result[0].getInteger("applicationId"))
//         assertEquals("EMAIL", result[0].getString("channel"))
//     }

//     @Test
//     fun `should throw DatabaseUnavailableException when MongoDB is not reachable`() {
//         // Only this stub is needed
//         `when`(mongoCollection.find(any(Document::class.java)))
//             .thenThrow(RuntimeException("Connection refused"))

//         val exception = assertThrows(DatabaseUnavailableException::class.java) {
//             repository.findNotifications(
//                 applicationId = 101,
//                 fromDate = "2024-01-01",
//                 toDate = "2024-01-31",
//                 channel = "EMAIL"
//             )
//         }

//         assertTrue(exception.message!!.contains("MongoDB is not reachable"))
//     }
// }
