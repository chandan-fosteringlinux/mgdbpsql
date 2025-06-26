
package com.indigo.get.details.by.providerId.model

import java.math.BigDecimal


data class ProviderChannelPrice(
    val channelType: String,
    val pricePerMessage: BigDecimal
)
