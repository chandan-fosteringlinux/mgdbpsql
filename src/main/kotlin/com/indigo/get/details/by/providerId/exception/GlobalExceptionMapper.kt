// package com.indigo.get.details.by.providerId.exception

// import jakarta.ws.rs.core.Response
// import jakarta.ws.rs.ext.ExceptionMapper
// import jakarta.ws.rs.ext.Provider
// import jakarta.ws.rs.core.MediaType

// @Provider
// class GlobalExceptionMapper : ExceptionMapper<Throwable> {
//     override fun toResponse(exception: Throwable): Response {
//         val status = when (exception) {
//             is DatabaseUnavailableException -> Response.Status.INTERNAL_SERVER_ERROR
//             is InvalidProviderIdException -> Response.Status.BAD_REQUEST
//             else -> Response.Status.INTERNAL_SERVER_ERROR
//         }

//         val errorBody = mapOf(
//             "error" to exception.message,
//             "type" to exception::class.simpleName
//         )

//         return Response.status(status)
//             .entity(errorBody)
//             .type(MediaType.APPLICATION_JSON)
//             .build()
//     }
// }

package com.indigo.get.details.by.providerId.exception

import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider
import jakarta.ws.rs.core.MediaType

@Provider
class GlobalExceptionMapper : ExceptionMapper<Throwable> {
    override fun toResponse(exception: Throwable): Response {
        val status = when (exception) {
            is DatabaseUnavailableException -> Response.Status.INTERNAL_SERVER_ERROR
            is InvalidProviderIdException -> Response.Status.BAD_REQUEST
            else -> Response.Status.INTERNAL_SERVER_ERROR
        }

        // Build response body conditionally
        val errorBody = mutableMapOf<String, Any?>(
            "error" to (exception.message ?: "Internal server error")
        )

        // Only include error type if it's not INTERNAL_SERVER_ERROR
        if (status != Response.Status.INTERNAL_SERVER_ERROR) {
            errorBody["type"] = exception::class.simpleName
        }

        return Response.status(status)
            .entity(errorBody)
            .type(MediaType.APPLICATION_JSON)
            .build()
    }
}
