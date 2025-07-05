
package com.indigo.get.details.by.providerId.model

import java.math.BigDecimal

data class NotificationStats(
    val channel: String,
    val count: Int,
    val totalCharge: BigDecimal
)