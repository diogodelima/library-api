package com.diogo.library.services

import com.diogo.library.domain.book.Book
import com.diogo.library.repositories.BookRepository
import org.springframework.stereotype.Service

@Service
class BookService(

    private val bookRepository: BookRepository

) {

    fun getById(id: Long): Book? {
        return bookRepository.findById(id).orElse(null)
    }

    fun save(book: Book): Book {
        return bookRepository.save(book)
    }

    fun delete(id: Long) {
        bookRepository.deleteById(id)
    }

}