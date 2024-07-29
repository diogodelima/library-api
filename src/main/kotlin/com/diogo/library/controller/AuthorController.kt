package com.diogo.library.controller

import com.diogo.library.domain.author.Author
import com.diogo.library.dto.AuthorCreateRequestDto
import com.diogo.library.dto.AuthorResponseDto
import com.diogo.library.dto.AuthorUpdateRequestDto
import com.diogo.library.exceptions.AuthorNotFoundException
import com.diogo.library.exceptions.BookNotFoundException
import com.diogo.library.services.AuthorService
import com.diogo.library.services.BookService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/author")
class AuthorController(

    private val authorService: AuthorService,
    private val bookService: BookService

) {

    @GetMapping("/{authorId}")
    fun get(@PathVariable authorId: Long): ResponseEntity<AuthorResponseDto> {
        val author = authorService.getById(authorId) ?: throw AuthorNotFoundException()
        return ResponseEntity.ok(
            AuthorResponseDto(
                author.id!!, author.firstName, author.lastName, author.biography,
                author.books.map { it.id!! }
            )
        )
    }

    @PostMapping("/create")
    fun create(@RequestBody @Valid request: AuthorCreateRequestDto): ResponseEntity<AuthorResponseDto> {

        val author = authorService.save(
            Author(
                firstName = request.firstName,
                lastName = request.lastName,
                biography = request.biography
            )
        )

        return ResponseEntity.ok(
            AuthorResponseDto(
                author.id!!, author.firstName, author.lastName, author.biography,
                author.books.map { it.id!! }
            )
        )
    }

    @PutMapping("/update")
    fun update(@RequestBody @Valid request: AuthorUpdateRequestDto): ResponseEntity<AuthorResponseDto> {

        val author = authorService.getById(request.id) ?: throw AuthorNotFoundException()

        author.firstName = request.firstName ?: author.firstName
        author.lastName = request.lastName ?: author.lastName
        author.biography = request.biography ?: author.biography

        return ResponseEntity.ok(
            AuthorResponseDto(
                author.id!!, author.firstName, author.lastName, author.biography,
                author.books.map { it.id!! }
            )
        )
    }

    @DeleteMapping("/delete/{authorId}")
    fun delete(@PathVariable authorId: Long): ResponseEntity<String> {
        authorService.delete(authorId)
        return ResponseEntity.ok("Author deleted successfully")
    }

}