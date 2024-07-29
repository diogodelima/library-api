package com.diogo.library.services

import com.diogo.library.domain.author.Author
import com.diogo.library.repositories.AuthorRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class AuthorService(

    private val authorRepository: AuthorRepository

) {

    fun getById(id: Long): Author? {
        return authorRepository.findById(id).orElse(null)
    }

    fun save(author: Author): Author {
        return authorRepository.save(author)
    }

    fun delete(id: Long) {
        authorRepository.deleteById(id)
    }

    fun getPage(page: Int, pageSize: Int): Page<Author> {
        val pageRequest = PageRequest.of(page - 1, pageSize)
        return authorRepository.findAll(pageRequest)
    }


}