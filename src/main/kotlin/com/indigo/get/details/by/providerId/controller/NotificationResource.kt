// import jakarta.ws.rs.*
// import jakarta.ws.rs.core.MediaType
// import org.bson.Document

// @Path("/notification/v1")
// @Produces(MediaType.APPLICATION_JSON)
// class NotificationResource(private val service: NotificationService) {

//     @GET
//     @Path("/{partnerId}/getDetails")
//     fun getDetails(
//         @PathParam("partnerId") partnerId: String,
//         @QueryParam("applicationId") applicationId: Int,
//         @QueryParam("fromDate") fromDate: String,
//         @QueryParam("toDate") toDate: String,
//         @QueryParam("channel") channel: String
//     ): List<Document> {
//         return service.findNotifications(partnerId, applicationId, fromDate, toDate, channel)
//     }
// }




// import jakarta.ws.rs.*
// import jakarta.ws.rs.core.MediaType
// import jakarta.ws.rs.core.Response


// @Path("/notification/v1")
// @Produces(MediaType.APPLICATION_JSON)
// class NotificationResource(private val service: NotificationService) {

//     @GET
//     @Path("/{providerId}/getDetails")
//     fun getDetails(
//         @PathParam("providerId") providerId: String,
//         @QueryParam("applicationId") applicationId: Int?,
//         @QueryParam("fromDate") fromDate: String?,
//         @QueryParam("toDate") toDate: String?,
//         @QueryParam("channel") channel: String?
//     ): Document {
//         return service.findNotifications(applicationId, fromDate, toDate, channel)
//     }
// }







import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("/notification/v1")
@Produces(MediaType.APPLICATION_JSON)
class NotificationResource(private val service: NotificationService) {

    @GET
    @Path("/{providerId}/getDetails")
    fun getDetails(
        @PathParam("providerId") providerId: String,
        @QueryParam("applicationId") applicationId: Int?,
        @QueryParam("fromDate") fromDate: String?,
        @QueryParam("toDate") toDate: String?,
        @QueryParam("channel") channel: String?
    ): Map<String, Any> {
        return service.findNotifications(providerId, applicationId, fromDate, toDate, channel)
    }
}



