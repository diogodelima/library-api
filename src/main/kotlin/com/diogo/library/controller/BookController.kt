package com.diogo.library.controller

import com.diogo.library.domain.book.Book
import com.diogo.library.domain.language.Language
import com.diogo.library.dto.BookCreateRequestDto
import com.diogo.library.dto.BookResponseDto
import com.diogo.library.dto.BookUpdateRequestDto
import com.diogo.library.dto.PageResponseDto
import com.diogo.library.exceptions.*
import com.diogo.library.services.AuthorService
import com.diogo.library.services.BookService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.time.format.DateTimeParseException

@RestController
@RequestMapping("/book")
class BookController(

    private val bookService: BookService,
    private val authorService: AuthorService,

    @Value("\${page.size}")
    private val pageSize: Int

) {

    @GetMapping("/{bookId}")
    fun get(@PathVariable bookId: Long): ResponseEntity<BookResponseDto> {
        val book = bookService.getById(bookId) ?: throw BookNotFoundException()
        return ResponseEntity.ok(
            BookResponseDto(
                book.id!!, book.title, book.isbn, book.releaseDate.toString(), book.synopsis,
                book.language.name, book.publisher, book.collection, book.author.id!!
            )
        )
    }

    @GetMapping("/list/{nPage}")
    fun list(@PathVariable nPage: Int): ResponseEntity<PageResponseDto<BookResponseDto>> {

        if (nPage <= 0)
            throw PageInvalidException()

        val page = bookService.getPage(nPage, pageSize)

        return ResponseEntity.ok(
            PageResponseDto(
                page.content.map {
                    BookResponseDto(
                        it.id!!, it.title, it.isbn, it.releaseDate.toString(), it.synopsis,
                        it.language.name, it.publisher, it.collection, it.author.id!!
                    )
                },
                page.number + 1,
                page.size,
                page.totalPages
            )
        )
    }

    @PostMapping("/create")
    fun create(@RequestBody @Valid request: BookCreateRequestDto): ResponseEntity<BookResponseDto> {

        val author = authorService.getById(request.authorId) ?: throw AuthorNotFoundException()

        val book = bookService.save(
            Book(
                title = request.title,
                isbn = request.isbn,
                releaseDate = try { LocalDate.parse(request.releaseDate) } catch (e: DateTimeParseException) { throw DateFormatException() },
                synopsis = request.synopsis,
                language = try { Language.valueOf(request.language) } catch (e: Exception) { throw LanguageNotFoundException() },
                publisher = request.publisher,
                collection = request.collection,
                author = author
            )
        )

        author.addBook(book)
        authorService.save(author)

        return ResponseEntity.ok(
            BookResponseDto(
                book.id!!, book.title, book.isbn, book.releaseDate.toString(), book.synopsis,
                book.language.name, book.publisher, book.collection, book.author.id!!
            )
        )
    }

    @PutMapping("/update")
    fun update(@RequestBody @Valid request: BookUpdateRequestDto): ResponseEntity<BookResponseDto> {

        val book = bookService.getById(request.id) ?: throw BookNotFoundException()

        book.isbn = request.isbn ?: book.isbn
        book.synopsis = request.synopsis ?: book.synopsis
        book.publisher = request.publisher ?: book.publisher
        book.collection = request.collection ?: book.collection

        book.releaseDate = request.releaseDate?.let {
            try { LocalDate.parse(it) } catch (e: DateTimeParseException) { throw DateFormatException() }
        } ?: book.releaseDate

        book.language = request.language?.let {
            try { Language.valueOf(request.language) } catch (e: Exception) { throw LanguageNotFoundException() }
        } ?: book.language

        if (request.authorId != null && book.author.id != request.id) {

            val newAuthor = authorService.getById(request.authorId) ?: throw AuthorNotFoundException()
            val lastAuthor = book.author

            book.author = newAuthor

            lastAuthor.removeBook(book)
            newAuthor.addBook(book)
        }

        bookService.save(book)

        return ResponseEntity.ok(
            BookResponseDto(
                book.id!!, book.title, book.isbn, book.releaseDate.toString(), book.synopsis,
                book.language.name, book.publisher, book.collection, book.author.id!!
            )
        )

    }

    @DeleteMapping("/delete/{bookId}")
    fun delete(@PathVariable bookId: Long): ResponseEntity<String> {
        bookService.delete(bookId)
        return ResponseEntity.ok("Book deleted successfully")
    }

}