package com.diogo.library.controller

import com.diogo.library.domain.author.Author
import com.diogo.library.dto.*
import com.diogo.library.exceptions.AuthorNotFoundException
import com.diogo.library.exceptions.PageInvalidException
import com.diogo.library.services.AuthorService
import com.diogo.library.services.BookService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/author")
class AuthorController(

    private val authorService: AuthorService,
    private val bookService: BookService,

    @Value("\${page.size}")
    private val pageSize: Int

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

    @GetMapping("/list/{nPage}")
    fun list(@PathVariable nPage: Int): ResponseEntity<PageResponseDto<AuthorResponseDto>> {

        if (nPage <= 0)
            throw PageInvalidException()

        val page = authorService.getPage(nPage, pageSize)

        return ResponseEntity.ok(
            PageResponseDto(
                page.content.map {
                    AuthorResponseDto(
                        it.id!!, it.firstName, it.lastName, it.biography, it.books.map { book -> book.id!! }
                    )
                },
                page.number + 1,
                page.size,
                page.totalPages
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