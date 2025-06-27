package com.indigo.get.details.by.providerId.controller

import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import com.indigo.get.details.by.providerId.service.NotificationService
import com.indigo.get.details.by.providerId.DTO.NotificationResponse
import org.jboss.logging.Logger

@Path("/notification/v1")
@Produces(MediaType.APPLICATION_JSON)
class NotificationController(private val service: NotificationService) {

    private companion object {
        private val LOG: Logger = Logger.getLogger(NotificationController::class.java)
    }

    @GET
    @Path("/{providerId}/getDetails")
    fun getDetails(
        @PathParam("providerId") providerId: String,
        @QueryParam("applicationId") applicationId: Int?,
        @QueryParam("fromDate") fromDate: String?,
        @QueryParam("toDate") toDate: String?,
        @QueryParam("channel") channel: String?
    ): NotificationResponse {
        LOG.info("Received request for providerId=$providerId, applicationId=$applicationId, fromDate=$fromDate, toDate=$toDate, channel=$channel")

        val response = service.getNotifications(providerId, applicationId, fromDate, toDate, channel)

        LOG.info("Successfully delivered notification data to the user for providerId=$providerId")
        return response
    }
}
