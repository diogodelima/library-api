package com.diogo.library.domain.book

import com.diogo.library.domain.author.Author
import com.diogo.library.domain.language.Language
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "book")
data class Book(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val title: String,

    @Column(nullable = false)
    val isbn: String,

    @Column(nullable = false)
    val releaseDate: LocalDate,

    @Column(nullable = false, columnDefinition = "TEXT")
    val synopsis: String,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val language: Language,

    @Column(nullable = false)
    val publisher: String,

    @Column(nullable = false)
    val collection: String,

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    val author: Author

)