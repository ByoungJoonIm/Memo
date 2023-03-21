package bj.max.lim.blog.search.server.controller.advice

import bj.max.lim.blog.search.common.exception.AllClientUnavailableException
import bj.max.lim.blog.search.common.exception.FieldOutOfRangeException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandlers {

    @ExceptionHandler(FieldOutOfRangeException::class)
    fun handleFieldOutOfRangeException(fieldOutOfRangeException: FieldOutOfRangeException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(fieldOutOfRangeException.message)
    }

    @ExceptionHandler(AllClientUnavailableException::class)
    fun handleAllClientUnavailableException(allClientUnavailableException: AllClientUnavailableException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(allClientUnavailableException.message)
    }
}
