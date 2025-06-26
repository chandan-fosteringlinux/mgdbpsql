

// import jakarta.enterprise.context.ApplicationScoped
// import io.smallrye.mutiny.Uni

// @ApplicationScoped
// class NotificationMongoRepository {

//     fun findMatchingNotifications(
//         partnerId: String,
//         applicationId: Int,
//         channel: String,
//         fromDate: String,
//         toDate: String
//     ): Uni<List<NotificationDocument>> {
//         return NotificationDocument.find(
//             """
//             {
//                 "partnerId": "$partnerId",
//                 "applicationId": $applicationId,
//                 "channel": "$channel",
//                 "timestamp": { 
//                     "\$gte": "$fromDate", 
//                     "\$lte": "$toDate" 
//                 }
//             }
//             """
//         ).list()
//     }
// }
