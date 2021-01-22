package com.haorui.gallerybackend.config

import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import javax.servlet.http.HttpServletRequest


@Configuration
class Config {
}


class AppNotFoundException(message: String?) : RuntimeException(message)

data class ErrorInfo(
    val timestamp: Long = System.currentTimeMillis(),
    val status: Int,
    val error: String,
    val message: String?,
    val path: String,
)

@ControllerAdvice
class GlobalDefaultExceptionHandler {

    @ExceptionHandler(RuntimeException::class)
    @ResponseBody
    fun handleBadRequest(req: HttpServletRequest, ex: Exception): ErrorInfo {
        ex.printStackTrace()
        return ErrorInfo(
            message = ex.message,
            error = ex.javaClass.name,
            status = HttpStatus.BAD_REQUEST.value(),
            path = req.servletPath
        )
    }
}
