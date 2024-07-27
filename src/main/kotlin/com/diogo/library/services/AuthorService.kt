package com.diogo.library.services

import com.diogo.library.domain.author.Author
import com.diogo.library.repositories.AuthorRepository
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

}