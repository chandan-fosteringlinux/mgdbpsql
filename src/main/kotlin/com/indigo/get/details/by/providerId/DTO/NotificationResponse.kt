
package com.indigo.get.details.by.providerId.DTO

import org.bson.Document
import com.indigo.get.details.by.providerId.model.NotificationStats

data class NotificationResponse(
    val notifications: List<Document>,
    val stats: List<NotificationStats>
)
