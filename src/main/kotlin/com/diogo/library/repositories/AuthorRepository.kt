package com.diogo.library.repositories

import com.diogo.library.domain.author.Author
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthorRepository: JpaRepository<Author, Long>