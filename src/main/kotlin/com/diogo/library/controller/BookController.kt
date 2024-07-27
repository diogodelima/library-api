package com.diogo.library.controller

import com.diogo.library.dto.BookResponseDto
import com.diogo.library.exceptions.BookNotFoundException
import com.diogo.library.services.BookService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/book")
class BookController(

    private val bookService: BookService

) {

    @GetMapping("/{bookId}")
    fun get(@PathVariable bookId: Long): ResponseEntity<BookResponseDto> {
        val book = bookService.getById(bookId) ?: throw BookNotFoundException()
        return ResponseEntity.ok(
            BookResponseDto(book.id!!, book.title, book.isbn, book.releaseDate, book.author.id!!)
        )
    }

}