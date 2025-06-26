

package com.indigo.get.details.by.providerId.controller

import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import com.indigo.get.details.by.providerId.service.NotificationService
import com.indigo.get.details.by.providerId.DTO.NotificationResponse


@Path("/notification/v1")
@Produces(MediaType.APPLICATION_JSON)
class NotificationController(private val service: NotificationService) {

    @GET
    @Path("/{providerId}/getDetails")
    fun getDetails(
        @PathParam("providerId") providerId: String,
        @QueryParam("applicationId") applicationId: Int?,
        @QueryParam("fromDate") fromDate: String?,
        @QueryParam("toDate") toDate: String?,
        @QueryParam("channel") channel: String?
    ): NotificationResponse {
        return service.getNotifications(providerId, applicationId, fromDate, toDate, channel)
    }
}
